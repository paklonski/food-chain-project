package channels;

import states.*;
import product.*;
import operations.*;
import chainParticipants.*;

import java.util.*;

import static simulation.Main.LOGGER;

/**
 * The class describes the condition and behavior of the type of a food channel
 * through which operations with vegetables are performed.
 * The class is an implementation of the Singleton design pattern.
 *
 * @author Artem Hurbych, Pavel Paklonski
 */
public final class VegetableChannel {

    private static VegetableChannel vegetableChannel;
    private final List<Party> trustedParticipants;
    private List<Party> currentParticipants = new ArrayList<>();
    private TransactionInformer transactionInformer;

    private Party applicant = null;
    private Party executor = null;
    private FoodEntity foodEntity = null;
    private List<FoodEnum> allowedEntities;

    private VegetableChannel(List<Party> trustedParticipants, List<FoodEnum> allowedEntities, TransactionInformer transactionInformer) {
        this.trustedParticipants = Collections.unmodifiableList(trustedParticipants);
        this.allowedEntities = allowedEntities;
        this.transactionInformer = transactionInformer;
    }

    public static VegetableChannel getVegetableChannel(List<Party> trustedParticipants,
                                                       List<FoodEnum> allowedEntities, TransactionInformer transactionInformer) {
        if (vegetableChannel == null) {
            vegetableChannel = new VegetableChannel(trustedParticipants, allowedEntities, transactionInformer);
        }
        return vegetableChannel;
    }

    public static VegetableChannel getVegetableChannel() {
        return vegetableChannel;
    }

    public List<FoodEnum> getAllowedEntities() {
        return allowedEntities;
    }

    /**
     * Registers the chain participant as current, if it is trusted.
     *
     * @param party the chain participant
     */
    public void register(Party party) {
        if (trustedParticipants.contains(party)) {
            currentParticipants.add(party);
        } else {
            LOGGER.warning("VEGETABLE CHANNEL: Access to the vegetable channel for this type " +
                    "of chain participant is denied.");
        }
    }

    /**
     * Unregisters the chain participant from the list of current.
     *
     * @param party the chain participant
     */
    public void unregister(Party party) {
        if (currentParticipants.contains(party) && !party.equals(applicant) && !party.equals(executor)) {
            currentParticipants.remove(party);
        } else {
            LOGGER.warning("VEGETABLE CHANNEL: This party is not a participant of the vegetable channel" +
                    " or you need to free the vegetable channel.");
        }
    }

    private boolean isRegister(Party party) {
        return currentParticipants.contains(party);
    }

    private Party findExecutor(Request request) {
        Collections.reverse(currentParticipants);
        for (Party party : currentParticipants) {
            if (party.isAgreeToExecute(request)) {
                Collections.reverse(currentParticipants);
                return party;
            }
        }
        Collections.reverse(currentParticipants);
        return null;
    }

    private boolean isNotReadyForTransportation() {
        return this.foodEntity != null && foodEntity.getState() instanceof UnpackedState;
    }

    private void executeRequest(Request request) {
        PaymentChannel.getPaymentChannel().makePayment(applicant, executor, request.getPrice());
        // send food to the applicant
        applicant.addFood(foodEntity);
        // create a new transaction, write to the list and notify all participants
        Transaction transaction = new Transaction(request, executor, executor.getLastHash());
        transactionInformer.notify(transaction);
    }

    private void resetChannel() {
        // reset an executor and a food entity
        this.foodEntity = null;
        this.executor = null;
        this.applicant = null;
    }

    /**
     * Processes the request by linking two chain participants and passing the product.
     * Starts the payment process if the product can be produced and can be paid.
     *
     * @param request the request
     */
    public void makeRequest(Request request) {

        if (allowedEntities.stream().anyMatch(name -> request.getFoodEnum().equals(name))) {
            if (isRegister(request.getApplicant()) && applicant == null) {
                this.applicant = request.getApplicant();
                this.executor = findExecutor(request);
            }
        }

        if (this.executor != null) {
            request.setPrice(executor.askPrice(request.getFoodEnum(), request.getQuantity()));
            if (applicant.isAgreeToPay(request.getPrice())) {
                this.foodEntity = executor.process(request);
            } else {
                System.out.println(applicant.getName() + " has no money.");
            }
        }

        if (isNotReadyForTransportation()) {
            LOGGER.warning("Product must be packed to be transported");
        }

        if (this.foodEntity != null) {
            executeRequest(request);
        }

        resetChannel();
    }
}



