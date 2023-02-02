// This code is not a solution to any particular homework assignment at the University of Virginia

import java.util.*;

public class BinaryHeap {

    private int size;
    private int capacity;

    private int[] heap;

    public static void main(String[] args) {

        if (true) {
            BinaryHeap binaryHeap = new BinaryHeap();
            binaryHeap.push(1);
            System.out.println(binaryHeap);
            binaryHeap.push(2);
            System.out.println(binaryHeap);
            binaryHeap.push(3);
            System.out.println(binaryHeap);
            binaryHeap.push(4);
            System.out.println(binaryHeap);
            binaryHeap.push(5);
            System.out.println(binaryHeap);
            binaryHeap.push(6);
            System.out.println(binaryHeap);

            System.out.println();

            System.out.println(binaryHeap.peek());

            System.out.println();

            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);

            System.out.println();
            System.out.println("*****************************");
            System.out.println();

            binaryHeap.push(1);
            System.out.println(binaryHeap);
            binaryHeap.push(2);
            System.out.println(binaryHeap);
            binaryHeap.push(3);
            System.out.println(binaryHeap);
            binaryHeap.push(4);
            System.out.println(binaryHeap);
            binaryHeap.push(5);
            System.out.println(binaryHeap);
            binaryHeap.push(6);
            System.out.println(binaryHeap);

            System.out.println();

            System.out.println(binaryHeap.peek());

            System.out.println();

            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);
            binaryHeap.poll();
            System.out.println(binaryHeap);
        }

        while (true) {
            System.out.println("What size binary heap priority queue would you like to test?");
            Scanner scanner = new Scanner(System.in);
            int binaryHeapSize = 0;
            binaryHeapSize = scanner.nextInt();

            System.out.println("Creating a binary heap with a size of " + binaryHeapSize + " integers...");
            BinaryHeap binaryHeap = new BinaryHeap();

            ArrayList<Integer> initialArray = new ArrayList<>(binaryHeapSize);
            for(int i = 0; i < binaryHeapSize; i++){
                initialArray.add(i+1);
            }

            Collections.shuffle(initialArray);

//            Random random = new Random();
            for (int i = 0; i < binaryHeapSize; i++) {
                binaryHeap.push(initialArray.get(i));
//                binaryHeap.push(random.nextInt(binaryHeapSize) + 1);
            }

            System.out.println("Binary heap created.");
            System.out.println(binaryHeap);

            System.out.println("Now testing its correctness by polling all elements into a list...");
            int[] testArray = new int[binaryHeapSize];
            for (int i = 0; i < testArray.length; i++) {
                testArray[i] = binaryHeap.poll();
            }
            System.out.println(Arrays.toString(testArray) + "\nTest complete.\n");
        }
    }

    public BinaryHeap() {
        this(0);
    }

    public BinaryHeap(int capacity) {

        if (capacity < 1) {
            this.capacity = 1;
        } else {
            this.capacity = capacity;
        }
        this.size = 0;
        heap = new int[this.capacity + 1]; // the zeroth element is blank
    }

    public void push(int newVal) {

        if (capacity > 0) {
            // check for duplicates
            for (int val : heap) {
                if (val == newVal) {
                    return;
                }
            }
        }

        // resize if at capacity
        if (size == capacity) {
            heap = Arrays.copyOf(heap, capacity * 2 + 1);
            capacity = capacity * 2;
        }

        // add value to bottom of tree/end of array
        size++;
        int newValIndex = size;
        heap[newValIndex] = newVal;

        while ((newValIndex / 2) != 0) { // while newVal is not the root
            if (heap[newValIndex] < heap[newValIndex / 2]) {
                swap(heap, newValIndex, newValIndex / 2);
            }
            newValIndex = newValIndex / 2;
        }
    }

    public int peek() {
        return heap[1];
    }

    public int poll() {
        int priorityVal = heap[1];

        // root value gets taken out, gets swapped with last value
        try {
            heap[1] = heap[size];
        } catch (ArrayIndexOutOfBoundsException e) {
            return priorityVal;
        }
        heap[size] = 0; // returning to null value in this array
        size--;
        int heightOfTree = (int) (Math.log(size) / Math.log(2)) + 1; // find height of tree by taking log2(size) and rounding up to nearest integer
        int newLevelOfLastValue = 1; // last value starts at root
        int lastValueCurrentIndex = 1; // index of root is 1
        if (size == 0) {
            return priorityVal;
        }

        while ((newLevelOfLastValue != heightOfTree)) {
            if (newLevelOfLastValue < (heightOfTree - 1)) { // last value not yet reached second level from bottom of tree
                boolean leftSmaller = (heap[lastValueCurrentIndex * 2] < heap[lastValueCurrentIndex * 2 + 1]);
                if (leftSmaller) {
                    if (heap[lastValueCurrentIndex * 2] < heap[lastValueCurrentIndex]) {
                        swap(heap, lastValueCurrentIndex, lastValueCurrentIndex * 2);
                        lastValueCurrentIndex = lastValueCurrentIndex * 2;
                        newLevelOfLastValue++;
                    } else {
                        break;
                    }
                } else {
                    if (heap[(lastValueCurrentIndex * 2) + 1] < heap[lastValueCurrentIndex]) {
                        swap(heap, lastValueCurrentIndex, (lastValueCurrentIndex * 2) + 1);
                        lastValueCurrentIndex = (lastValueCurrentIndex * 2) + 1;
                        newLevelOfLastValue++;
                    } else {
                        break;
                    }
                }
            } else {
                boolean lastValueHasChildren = true;
                try {
                    if (heap[lastValueCurrentIndex * 2] == 0) {
                        break; // last value does not have children
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    break; // last value does not have children
                }
                if (lastValueHasChildren) {
                    if ((size % 2) == 1) { // does last value have both a left and right child?
                        boolean leftSmaller = (heap[lastValueCurrentIndex * 2] < heap[lastValueCurrentIndex * 2 + 1]);
                        if (leftSmaller) {
                            if (heap[lastValueCurrentIndex * 2] < heap[lastValueCurrentIndex]) {
                                swap(heap, lastValueCurrentIndex, lastValueCurrentIndex * 2);
                                lastValueCurrentIndex = lastValueCurrentIndex * 2;
                                newLevelOfLastValue++;
                            }
                            break;
                        } else {
                            if (heap[(lastValueCurrentIndex * 2) + 1] < heap[lastValueCurrentIndex]) {
                                swap(heap, lastValueCurrentIndex, (lastValueCurrentIndex * 2) + 1);
                                lastValueCurrentIndex = (lastValueCurrentIndex * 2) + 1;
                                newLevelOfLastValue++;
                            }
                            break;
                        }
                    } else { // last value just has one child on the left
                        if (heap[lastValueCurrentIndex * 2] < heap[lastValueCurrentIndex]) {
                            swap(heap, lastValueCurrentIndex, lastValueCurrentIndex * 2);
                            lastValueCurrentIndex = lastValueCurrentIndex * 2;
                            newLevelOfLastValue++;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        if ((size <= (capacity / 2)) && (capacity != 1)) { // size down to save memory
            heap = Arrays.copyOf(heap, (capacity / 2) + 1);
            capacity = capacity / 2;
        }
        return priorityVal;
    }

    private static void swap(int[] array, int index1, int index2) {
        int tempVal = array[index1];
        array[index1] = array[index2];
        array[index2] = tempVal;
    }

    public String toString() {
        return Arrays.toString(heap);
    }
}
