
Score Recorder test:

- it needs java 7 to run since I am using a switch case on a String.
  
- for a more complete solution would be opportune to check that request
  have been made with the right verb (GET or POST).

- Since the application should run forever with limited memory,
  I am storing only the top 15 scores per level, 
  since any additional score would never be returned.

