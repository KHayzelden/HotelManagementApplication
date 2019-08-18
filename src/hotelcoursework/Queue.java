package hotelcoursework;

public class Queue {
    
    private String queuedNames[] = new String[7];
    private int start, end ;
    
    public Queue()
    {
        start = 0;
        end = 0;
    }

    public void addToQueue(String name) {
       
        if (end < 7)
        {         
            queuedNames[end] = name;
            end++;
        }
        else
        {            
            removeFromQueue(name);
            end = 0;
            queuedNames[end] = name;
            end++;
            
            if (start < 6) start++;
            else if (start == 6) start = 0;
        }
        
    }

    void removeFromQueue(String name) {
        
        System.out.println("start:" + start);
        System.out.println("end:" + end);
        
        if (start == 6) 
        {
            System.out.println(queuedNames[start] + " is now removed from the queue.");
            queuedNames[start] = null ;
            
            addToQueue(name);

            start = 1;
        } 
        else
        {
            System.out.println(queuedNames[start] + " is now removed from the queue.");
            queuedNames[start] = null ;
            
            start++;
        }
        
    }

    void displayQueue() {
        System.out.println("Queue display: ");
        for (int look = 0; look < 7; look++) 
        {
            System.out.print(" " + queuedNames[look]);
        }
        System.out.println("");
    }
     
    void topThree () {

        String name = null ;
        int x = start;
        
        if (x <= 3) start += 3;
        else if (x == 4) start = 0;
        else if (x == 5) start = 1;
        else if (x == 6) start = 2;
        
        while (x != start)
        {
            System.out.println(queuedNames[x] + " is now removed from the queue.");
            queuedNames[x] = null ;
            
            if (x < 6) x++;
            else x=0;
        }
    }
     
}