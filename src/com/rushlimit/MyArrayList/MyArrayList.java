package com.rushlimit.MyArrayList;

import java.util.Arrays;
import java.util.Collections;

/**
    A simple Array based ArrayList with Constant time insert/removal from front and back.
    Internal Array structure is type Comparable
    At push/pop-Back and push/pop-Front - array is re-sized based on size and space consumed.

    Implemented by: Mohammed Amin
 */
public class MyArrayList {

    protected Comparable[] mMyArray;
    protected int head, tail, size, numElements; // When outputting, head starts at (head + 1) ; tail is tail

    public MyArrayList() {
        mMyArray = new Comparable[10];
        size = 10;
        head = tail = size / 3;
    }

    public MyArrayList(int n) {
        mMyArray = new Comparable[n];
        size = mMyArray.length;
        head = tail = size / 3;
    }

    public void pushBack(Comparable i) {
        if (tail + 1 == mMyArray.length) {
            Comparable[] tempArr = new Comparable[size * 2];
            size *= 2;
            int newHead = size / 4;            // always starts at 1/4 ;
            for (int x = getHead(); x <= tail; x++) {
                tempArr[newHead++] = mMyArray[x];
            }
            head = size / 4;
            mMyArray = tempArr;
            tail = --head + numElements; // value of head changes ; reasoning: head is always head - 1
        }
        mMyArray[++tail] = i;
        numElements++;
    }

    public Comparable popBack() {
        if (head == tail)
            return null;
        Comparable returnObj = mMyArray[tail];
        mMyArray[tail] = null;
        tail--;
        numElements--;
        float fSize, fNumber;
        fSize = (float) size;
        fNumber = (float) numElements;
        if (size >= 40 && (fNumber / fSize) < 0.3) {
            int tempSize = size / 2; // 50% of array
            Comparable[] tempArr = new Comparable[tempSize];
            size = tempSize;
            int tempHeadO = getHead();
            head = size / 4;
            int tempHeadN = head;
            for (int x = 0; x < numElements; x++) {
                tempArr[tempHeadN++] = mMyArray[tempHeadO++];
            }
            mMyArray = tempArr;
            tail = --head + numElements;
            tempArr = null;
        }
        return returnObj;
    }

    public void pushFront(Comparable i) {
        if (head < 0) {
            Comparable[] tempArr = new Comparable[size * 2];
            size *= 2;
            int newHead = size / 4;
            for (int x = getHead(); x <= tail; x++) {
                tempArr[newHead++] = mMyArray[x];
            }
            head = size / 4;
            mMyArray = tempArr;
            tail = --head + numElements;
            tempArr = null;
        }
        mMyArray[head] = i;
        head--;
        numElements++;
    }

    protected Comparable popFront() {
        if (head == tail)
            return null;
        Comparable returnObj = mMyArray[getHead()];
        mMyArray[getHead()] = null;
        head++;
        numElements--;
        float fSize, fNumber;
        fSize = (float) size;
        fNumber = (float) numElements;
        if (size >= 50 && (fNumber / fSize) < 0.3) {
            int tempSize = size / 2; // 50% of array
            Comparable[] tempArr = new Comparable[tempSize];
            size = tempSize;
            int tempHeadO = getHead();
            head = size / 4;
            int tempHeadN = head;
            for (int x = 0; x < numElements; x++) {
                tempArr[tempHeadN++] = mMyArray[tempHeadO++];
            }
            mMyArray = tempArr;
            tail = --head + numElements; // --head because head starts at -1 before first
            tempArr = null;
        }
        return returnObj;
    }

    protected void sPopBack() {  // special pop for isFull() method
        if (head == tail)
            return;
        mMyArray[tail] = null;
        tail--;
    }

    public void print() {
        for (int i = getHead(); i <= tail; i++) {
            System.out.println(mMyArray[i]);
        }
        System.out.println();
    }

    public void printDev() {
        System.out.print("{");
        for (Object i : mMyArray)
            System.out.print(i + ", ");
        System.out.print("}");
        System.out.println();
    }

    protected void add(int i, Comparable c) {
        if (i < 0 || i >= numElements + 1) {
            System.out.println("Error in add, invalid index, <i> : " + i);
            return;
        }
        if(numElements == 0) {
            pushBack(c);
            return;
        }
        if(numElements != 0 && i == 0) {
            pushFront(c);
            return;
        }
        if(numElements != 0 && i == numElements) {
            pushBack(c);
            return;
        }
        boolean front, back;
        front = (head) != -1;
        back = (tail + 1) != size;
        int x = 0;
        if(front) {
            for(x = getHead(); x < i + getHead(); x++) {
                mMyArray[x - 1] = mMyArray[x];
            }
            mMyArray[x - 1] = c;
            head--;
            numElements++;
            return;
        }
        if(back) {
            for(x = tail; x >= i + getHead(); x--) {
                mMyArray[x + 1] = mMyArray[x];
            }
            mMyArray[x + 1] = c;
            tail++;
            numElements++;
            return;
        }
        if (tail + 1 == size) {
            Comparable[] tempArr = new Comparable[size * 2];
            size *= 2;
            int newHead = size / 4;            // always starts at 1/4 ;
            for (x = getHead(); x < i + getHead(); x++) {
                tempArr[newHead++] = mMyArray[x];
            }
            tempArr[newHead++] = c;
            for (x = i; x <= tail; x++) {
                tempArr[newHead++] = mMyArray[x];
            }
            numElements++;
            head = size / 4;
            mMyArray = tempArr;
            tail = --head + numElements; // value of head changes ; reasoning: head is always (head - 1)
        }
    }
    protected int set(int i, Comparable c) {
        if (i < 0 || i >= numElements || size == 0) {
            System.out.println("Error in set, invalid index");
            return i;
        }
        mMyArray[getHead() + i] = c;
        return 0;
    }

    protected Comparable remove(int i) {
        if (i < 0 || i >= numElements || size == 0)
            return null;
        Comparable tempC = peek(i);
        if(i == 0) {
            popFront();
            return tempC;
        }
        if(i == numElements - 1) {
            popBack();
            return tempC;
        }
        for(int x = i + 1; x < getTail(); x++) {
            mMyArray[x - 1 + getHead()] = mMyArray[x + getHead()];
        }
        popBack();
        return tempC;
    }

    private boolean remove(Comparable c) {
        if(numElements == 0)
            return false;
        if (numElements <= 20) {
            int index = -1;
            for(int i = getHead(); i <= tail; i++) {
                if(mMyArray[i].compareTo(c) == 0) {
                    index = i;
                    break;
                }
            }
            if(index !=  -1) {
                if (index == getHead()) {
                    popFront();
                    return true;
                } else if (index == tail) {
                    popBack();
                    return true;
                }
                for(int x = index + 1; x <= getTail(); x++) {
                    mMyArray[x - 1] = mMyArray[x];
                }
                popBack();
                return true;
            } else {
                return false;
            }
        }
        Comparable [] tempArr = mMyArray;
        Collections.sort(Arrays.asList(tempArr));
        int index = Arrays.binarySearch(tempArr, c);
        if(index < 0)
            return false;
        if(index == 0) {
            popFront();
            return true;
        }
        if(index == tail) {
            popBack();
            return true;
        }
        for(int x = index + 1 + getHead(); x <= getTail(); x++) {
            mMyArray[x - 1] = mMyArray[x];
        }
        popBack();
        tempArr = null;
        return true;
    }

    public Comparable[] getmMyArray() {
        return mMyArray;
    }

    protected void setmMyArray(Comparable[] mMyArray) {
        this.mMyArray = mMyArray;
    }

    public int getHead() {
        return head + 1;
    }

    public int getTail() {
        return tail;
    }

    protected void setHead(int head) {
        this.head = head;
    }

    private void setTail(int tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    private void setNumElements(int numElements) {
        this.numElements = numElements;
    }

    public int getNumElements() {
        return numElements;
    }

    public Comparable peek(int n) {
        if (n < 0 || n >= numElements || size == 0)
            return null;
        return mMyArray[getHead() + n];
    }

    public boolean isEmpty() {
        return numElements == 0;
    }

    public void clear() {
        Comparable[] tempArr = new Comparable[10];
        size = 10;
        numElements = 0;
        head = tail = size / 3;
        mMyArray = null;
        mMyArray = tempArr;
        tempArr = null;
    }

    public Comparable[] toArray() {
        Comparable[] tempArr = new Comparable[numElements];
        for (int i = 0; i < numElements; i++) {
            tempArr[i] = mMyArray[getHead() + i];
        }
        return tempArr;
    }
}