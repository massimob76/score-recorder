
Score Recorder test:

- it needs java 7 to run since I am using ConcurrentLinkedDeque 
  and switch case on a String.
  
- for a more complete solution would be opportune to add some integration tests for the Server class.
  Also a more robust mechanism for validating that the request have been made with the right parameter and method (GET or POST).

- Since the application should run forever with limited memory, 
  I have decided to drop the records for each level after 15 records 
  since they are never going to be retrieved.

- LevelScore has got large synchronized blocks.
  A more granular locking level or use of atomic operation would be opportune.
  Also a minimum score barrier would be advantageous since once the score board gets stable 
  there should be very few post-a-score that are actually managing to change the top scores board.

