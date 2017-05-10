# Story-driven Modeling

Our team used story-driven modeling in our development. Story-driven modeling is an Object-oriented modeling technique in which development begins with concrete, textual scenarios. The features discussed in these scenarios are then either drawn in a mock-up, or storyboarded. Mock-ups show GUI features and show each step of the scenario with a new wireframe. Storyboards use object diagrams in conjunction with each step of the scenario to show the current state of the system. From the storyboard, we can then derive a class diagram. Finally, we can go two routes: designing an algorithm or testing. In our group, we decided that testing was a better option because we could then implement test-driven development, especially since SDMLib, the story-driven modeling library makes this very easy. We used story-driven modeling throughout the development of our project.

## Ease Software Development

Because of story-driven modeling, we knew going into the project how the banking application would work. We developed diagrams and mockups before coding. These diagrams and customer requirements were then directly ported to code. We used tools like SDMLib to auto generate methods and cardinalities that easily translate to effective class diagrams and object oriented models.

Link to SDMLib can be found here: https://github.com/fujaba/SDMLib

## Adapt to Change

Throughout the development lifecycle, we knew that customers will have design or requirement changes that could interfere with the overall functionality and deployment of the application. Story-driven modeling and agile allows us to swiftly and efficiently make changes without interrupting production.


## Communicate with Stakeholders

Not all stakeholders speak the same language as our developers. Diagrams and mock-ups are an excellent tool to communicate abstract ideas and models without diving into overly complex or technical details. Story driven modeling bridges the language gap between non-technical customers and low-level engineers.

## Scenarios

> We will define a scenario of executing a certain functionality of a system as a series of steps describing a concrete example of the behavior of the ready developed system in practice and interaction with potential users. We will use the term story synonymously with scenario.

Norbisrath, Ulrich; Zündorf, Albert; Jubeh, Ruben. Story Driven Modeling (Kindle Locations 325-326).  . Kindle Edition.

---

A sample scenario can be found below (TRANSFER_MONEY Scenario 1):

Scenario 1: Sal successfully transfer $100 from her account to Sara's account.
- Sal needs to payback $100 that she borrowed from Sara yesterday.
- Sal currently has $150 in her account.
- She goes to the ATM, inserts her card, and enters her credentials.
- Sal selects the option to transfer money and the amount $100 to do.
- The machine checks Sal's account balance and prompts destination account info.
- Sal types in sara's account number as a destination.
- The machine checks Sara's account validity and prompts Sal Sara's name and account number.
- Sal presses "OK" drawable.button since the prompt info was correct.
- The machine withdraws $100 from Sal's account and deposits $100 to Sara's account.
- Sal selects to print her receipt and exits the system.
