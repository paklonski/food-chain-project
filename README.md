Foodchain project

A simplified implementation of the blockchain and then an implemention of a system for monitoring the flow of food from its cultivation, through their processing, storage, distribution, to sale to the customer.

This project was developed in collaboration with my colleague Artem Hurbych as a part of Object Design and Modeling Course at the CTU in Prague.

-------------

DESIGN PATTERNS

1. **Observer**        
The pattern is used for notifying all participants about new transaction that take place during the execution of the program.


2. **State**             
The pattern is used for changing and controlling state of product (packed, temporary packed, finally packed).


3. **Strategy**       
The pattern is used for different receipts to cook dish from ingredients.       
(package strategy, classes CookBorshch, CookPancakes, etc).


4. **Iterator**     
The pattern is used to iterate all events in party report.


5. **Singleton**      
The pattern is used for creating only one MeatChannel, VegetableChannel, ReadyMealChannel and only one PaymentChannel.


6. **Visitor**    
The pattern is used for filling prices of each party.

---------------------

LIST OF CLASSES      

package **chainParticipants**:      

**Party.java** - an interface for all parties which are involved in work.     
**ChainParticipant.java** - an abstract class describes the general state and behavior of a participant in a chain, including operations such as accepting a product, fulfilling a request, accepting payment, and others.          
**MeatFarmer.java** - the class describes the behavior of a farmer producing meat products.      
**VegetableFarmer.java** - the class describes the behavior of a farmer producing cereals.       
**Manufacturer.java** - the class describes the behavior of a manufacturer capable of creating certain types of products based on requests and the availability of available ingredients. If one or another ingredient is missing, the manufacturer makes a request for this type of ingredient to the farmer.      
**Storehouse.java** - the class describes the behavior of a warehouse with three types of refrigerators. Products supplied from farmers and manufactures are stored for a certain amount of time, after which they are passed on to the chain or participant who made a direct request for a particular type of product.       
**Seller.java** - the class describes the behavior of the seller, which is the prototype of the wholesale store. It is the fourth member of the chain.        
**Retailer.java** - the class describes the behavior of the seller, which is the prototype of retail stores. It is the fifth member of the chain. The main objective is the delivery of products to the final consumer, that is, the customer.         
**Customer.java** - the class describes the state and behavior of the buyer. The buyer can send requests, but can’t take action. It is the last sixth participant in the chain of participants.          

package **channels**:       

**MeatChannel.java** - all parties exchange meat products throw this channel. It is singletone.         
**VegetableChannel.java** - all parties exchange vegetable products throw this channel. It is singletone.        
**ReadyMealChannel.java** - all parties exchange ready meals throw this channel. It is singletone.       
**PaymentChannel.java** - all parties exchange money throw this channel. It is singletone.      

package **operations**:      

**Observable.java** - interface for TransactionInformer.       
**OperationEnum.java** - enum for operations that make the chain participants.      
**Transaction.java** - the object which creates objects to notificate all parties about some action.       
**Request.java** - the class for creating requests from some party.      
**TransactionInformer.java** - the class for notificate all parties about some action. Also uses for generating transaction reports.      

package **product**:      

**FoodEntity.java** - the class describes the general condition and behavior of an ingredient or product consisting of a list of ingredients.         
**FoodEnum.java** - enum for names of food entities.        

package **states**:      

**UnpackedState.java** - the class for representing state of product when it is unpacked, product is unpacked when it is used for cooking or just grown.     
**TemporaryPackedState.java** - the class for representing state of product when it is packed in temporary packing, product is temporary packed when it is transported between different parties or saving somewhere, customer cannot get temporary packed product.      
**FinalPackedState.java** - the class for representing state of product when it is packed in final packing or product in his final form. Only customer gets final packed product.     

package **strategy**:      

**ManufacturerStrategy.java** - the class interface for all cooking strategies.      
**CookBorshch.java** - the class for cooking borshch from given ingredient.     
**CookDraniki.java** - the class for cooking draniki from given ingredient.       
**CookIceCream.java** - the class for cooking ice cream from given ingredient.       
**CookKyivCutlet.java** - the class for cooking Kyiv cutlet from given ingredient.        
**CookPancakes.java** - the class for cooking pancakes from given ingredient.       


package **visitor**:     

**Visitor.java** - an inferface for Visitor design pattern.       
**PriceWriter.java** - the class for filling prices of each party.      


**Main.java** - the core of the program. It contains an imitation of the program, scenarios of possible errors and attempts to change its course.
