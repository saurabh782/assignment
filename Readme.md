Assumptions:

1. The solution assumes that the entities of the system are already initialised.Due to this the consumer,order,restaurant and delivery executive details have
not been populated by the code.
2. The solution assumes that in all cases a delivery executive has exactly 2 orders from exactly 2 different customers assigned to him and it 
finds the best possible route for that case.For other cases like  only 1 order or more than 2 orders by same or different customers it does not do any handling,
because these are product requirements which have not been specified and it might be possible that more than 2 orders might not be assigned to an agent 
in an actual production schenario due to technical limitations.

Approach:
1. Basically the task was to find the best possible route for an allocation made to a delivery executive.
2. I have exposed a function in the RouteController which actually takes the deliveryExecutiveId and then finds the bestPossibleListOfLocations 
that he should visit in order to deliver all the orders in minimum possible time.
3. For a 2 order scenario there are 6 possible ways to achieve that:
   executive_To_R1_To_C1_To_R2_To_C2
   executive_To_R2_To_C2_To_R1_To_C1 
   executive_To_R1_To_R2_To_C1_To_C2
   executive_To_R1_To_R2_To_C2_To_C1
   executive_To_R2_To_R1_To_C1_To_C2
   executive_To_R2_To_R1_To_C2_To_C1
4. The code calculates the time taken for all of these possible routes and chooses the route with the min time to accomplish the task and returns it.
5. If the code flow is observed from the routeController to the service then the logic written should be easy to understand.