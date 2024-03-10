// This class is the implementation of the weighted Union-Find algorithm and is optimized
// with path compression
// @author risorlet :)
package com.risorlet;
import java.util.Arrays;

public class WeightedUF {
    // the index of the id array represents the elements and the value stored in the
    // corresponding index is the root for that element
    private int[] id;

    // this array stores the weight of the connected-set rooted at index i
    // this is a helper array to iplement the weighted tree/set
    private int[] sz; 
    
    // initializes the id array and assigns each elements root to itself
    public WeightedUF(int n) {
        id = new int[n];
        sz = new int[n];
        for(int i=0; i<n; i++) {
            id[i] = i;
        }
        Arrays.fill(sz,1);
    }

    // connects the two sets (trees) containing the two element p & q
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if(sz[j]<sz[i]) { // when q belongs to the smaller set
            sz[i] += sz[j];
            id[j] = i;
        } else{ // when p belongs to the smaller set || weight of both the set are equal
            sz[j] += sz[i];
            id[i] = j;
        }
    }

    // checks if two element p and q are connected (directly/indirectly)
    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    // Returns the canonical element (root) of the set containing element p
    public int find(int p) {
        return root(p);
    }

    // counts the number of sets (trees)
    public int count() {
        int setCount = 0;
        for(int i = 0; i<id.length; i++) {
            if(id[i] == i) setCount++;
        }
        return setCount;
    }

    // finds the root of a given element i
    private int root (int i) {

        if(i<0 || i>=id.length) throw new IllegalArgumentException("Element does not exist!");
        if(id[i] == i) return i;

        int elem = i; // keeping a reference of the passed element to use for path compression

        while(id[i] != i) { // untill we get to the root, keep going up
            i = id[i]; // if i is not the root then select the parent and continue
        }
        while(id[elem] != elem) { // path compression optimization
            if(id[elem] == i){ // if elem is the immediate child of the root
                elem = i;
                continue;
            }

            int parent = id[elem]; // keep track of the parent of the current element
            id[elem] = i; // set the parent of the current element to the root
            elem = parent; //set the parent as the current element and repeat untill reaching root
        }
        return i;
    }

    @Override
    public String toString() {
        String str = "";
        for(int i = 0; i<id.length -1; i++) {
            str += id[i] + " | ";
        }
        str += id[id.length-1];
        return str;
    }
}