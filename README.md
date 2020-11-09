# test-app

﻿ How it works:

1) ActiveMQ broker is started. Connection and session are prepared. All maps with consumers, producers and queues are initialized
2) Each minute objects from file 'bids.json' are loaded and parsed as List<Bid> bids
3) For each item from list new thread is created, inside it queueBid(Bid bid) method called
4) In method queueBid(Bid bid) queue and consumer/producer are created if needed or got from maps. After that message is sent from producer and consumer receives it.
5) Bid is converted from json and is logged as <id>, <timestamp> (date format), <name> (queue name), <payload> (decoded from Base64)

﻿ To run this app you need:

1) Java SE Development Kit 8u172 for Windows ( http://www.oracle.com/technetwork/java//jdk8-downloads-2133151.html )
2) Go inside test-app folder
3) Open CLI
4) gradlew run

﻿ To stop this app you need:

1) In CLI press "Ctrl" + "C" and choose "Y"
