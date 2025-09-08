// MyAIDLInterface.aidl
package com.example.ch15_outer;

// Declare any non-default types here with import statements

interface MyAIDLInterface {

    int getDuration(); //추후에 MaxDuration으로 추가
    void start();
    void stop();
}

//java 코드처럼 코딩을 해야함,