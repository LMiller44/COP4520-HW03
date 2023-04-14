
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;


// LazyList class taken and modified from examples in Chapter 9.7: Lazy Synchronization
public class LazyList {

    Node head = new Node(Integer.MIN_VALUE);
    Node tail = new Node(Integer.MAX_VALUE);

    // constructor
    LazyList() {
        head.next = tail;
    }

    public class Node {
        int giftNum;
        Node next;
        boolean marked;

        // node constructor
        Node(int n) {
            giftNum = n;
            next = null;
            marked = false;
        }

        private ReentrantLock mutex = new ReentrantLock();
    }

    private boolean validate(Node previous, Node current) {
        return !previous.marked && !current.marked && previous.next == current;
    }

    public boolean add(int n) {
        int giftNum = n;

        if (head == null) {
            Node newNode = new Node(giftNum);
            head = newNode;
            return true;
        }

        while (true) {
            Node previous = head;
            Node current = head.next;

            // goes through the linked list until the correct location
            // for the node that's being inserted is found
            while (current.giftNum < giftNum) {
                previous = current;
                current = current.next;
            }

            previous.mutex.lock();
            try {
                current.mutex.lock();
                try {
                    if (validate (previous, current)) {
                        if (current.giftNum == giftNum) {
                            return false;
                        }
                        else {
                            Node newNode = new Node(giftNum);
                            newNode.next = current;
                            previous.next = newNode;
                            return true;
                        }
                    }
                }
                finally {
                    current.mutex.unlock();
                }
            }
            finally {
                previous.mutex.unlock();
            }
        }
    }


    public boolean remove() {
       
        Node previous = head;
        Node current = head.next;
        if (current.giftNum != Integer.MAX_VALUE){
            previous.mutex.lock();
            try {
                current.mutex.lock();
                try {
                    if (validate(previous, current)) {
                            current.marked = true;
                            previous.next = current.next;
                            return true;
                    }
                }
                finally {
                    current.mutex.unlock();
                }
            }
            finally {
                previous.mutex.unlock();
            }
        }
        return false;
    } 

    /*/
    public boolean remove(Node node) {
        int giftNum = node.giftNum;
        while (true) {
            Node previous = head;
            Node current = head.next;
            while (current.giftNum < giftNum) {
                previous = current;
                current = current.next;
            }

            previous.mutex.lock();
            try {
                current.mutex.lock();
                try {
                    if (validate(previous, current)) {
                        if (current.giftNum != giftNum) {
                            return false;
                        }
                        else {
                            current.marked = true;
                            previous.next = current.next;
                            return true;
                        }
                    }
                }
                finally {
                    current.mutex.unlock();
                }
            }
            finally {
                previous.mutex.unlock();
            }
        }
    } */

    // determines if the linked list contains a "gift" with the given gift number
    public boolean contains(int giftNum) {
        Node current = head;
        while (current.giftNum < giftNum)
            current = current.next;
        return current.giftNum == giftNum && !current.marked;
    }
}



















/*



public class LazyList {

    Node head = new Node(Integer.MIN_VALUE);
    Node tail = new Node(Integer.MAX_VALUE);

    head.next = tail;

    LazyList() {}

    public class Node {
        int giftNum;
        Node next;
        boolean marked;

        Node(int n) {
            giftNum = n;
            next = null;
            marked = false;
        }
    } */