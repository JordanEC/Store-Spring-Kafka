# Order creation and shipment using spring-kafka

Basic implementation of a module for order creation in a store and shipment dispatching. This application has 3 subprojects/modules:

order (producer): Orders are created through a POST API. If the order is valid, a message is sent to the shipment module.

shipment (consumer): Receives events from order module for the shipment process.

dtos: Provides a set of shared DTOs used by other modules.