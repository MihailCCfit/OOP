@echo off
if [%1]==[1] javac main/java/Main.java main/java/Heap.java -d forScript
if [%2]==[1] javadoc main/java/Main.java main/java/Heap.java -d forScript/doc
if [%3]==[1] java main/java/Main.java main/java/Heap.java
