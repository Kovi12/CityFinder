// ICityFinder.aidl
package com.example.cityfinder;
import com.example.cityfinder.ICallback;
// Declare any non-default types here with import statements
interface ICityFinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void RetrieveInfo(ICallback callback);
    double getDistance(double lat1, double long1, double lat2, double long2);
}