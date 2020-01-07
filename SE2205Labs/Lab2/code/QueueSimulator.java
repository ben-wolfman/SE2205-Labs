import java.lang.*;

public class QueueSimulator{
  public enum Event { ARRIVAL, DEPARTURE };
  private double currTime;
  private double arrivalRate;
  private double serviceTime;
  private double timeForNextArrival;
  private double timeForNextDeparture;
  private double totalSimTime;
  LinkedListQueue<Data> buffer = new LinkedListQueue<Data>();
  LinkedListQueue<Data> eventQueue = new LinkedListQueue<Data>();
  private Event e;

  public double getRandTime(double arrivalRate){
    double num, time1, max=1, min=0, randNUM;
    randNUM= Math.random();
    time1= (-1/arrivalRate) * (Math.log(1-randNUM));
    //System.out.println(time1);
    return time1;
  }

  public QueueSimulator(double aR, double servT, double simT){
    //Initialize variables of class
    //Current time starts at 0
    currTime = 0;
    //Class variables are initialized with parameters
    arrivalRate = aR;
    serviceTime = servT;
    totalSimTime = simT;
    //Calculate time for next Arrival and Departure
    timeForNextArrival = getRandTime(aR);
    timeForNextDeparture = servT + timeForNextArrival;
  }

  public double calcAverageWaitingTime(){
    double result = 0;
    int n = eventQueue.size();
    for (int i = 0; i < n; i++) {
      Data current = eventQueue.dequeue();
      result = result + (current.getDepartureTime() - current.getArrivalTime());
    }
    return result/n;
  }

  public double runSimulation(){
    while (currTime < totalSimTime) {
      //Checks whether the next event should be arrival or departure
      if ((timeForNextArrival < timeForNextDeparture) || buffer.isEmpty()) {
        e = Event.ARRIVAL;
      } else {
        e = Event.DEPARTURE;
      }
      if (e == Event.ARRIVAL) {
        //Updates the current time with the timeForNextArrival
        currTime += timeForNextArrival;
        //Create a new node, sets the arrival time to current time, and adds it to buffer
        Data d = new Data();
        d.setArrivalTime(currTime);
        buffer.enqueue(d);
        //Updates the next departure time to reflect time taken for arrival
        timeForNextDeparture = timeForNextDeparture - timeForNextArrival;
        //Gets a random next arrival time
        timeForNextArrival = getRandTime(arrivalRate);
      } else {
        //Updates the current time with the timeForNextDeparture
        currTime += timeForNextDeparture;
        //Store dequeue from buffer in variable exitNode, then set the departure time to current time
        Data exitNode = buffer.dequeue();
        exitNode.setDepartureTime(currTime);
        //Add the dequeued node to the eventQueue
        eventQueue.enqueue(exitNode);
        //Updates the next arrival time to reflect time taken for departure
        timeForNextArrival = timeForNextArrival - timeForNextDeparture;

        //If the buffer is empty, the time for next departure will be service time + time for next arrival
        //Otherwise, the time for next departure will just be service time
        if (buffer.isEmpty()) {
          timeForNextDeparture = timeForNextArrival + serviceTime;
        }
        else {
          timeForNextDeparture = serviceTime;
        }
      }
    }
    return calcAverageWaitingTime();
  }
}
