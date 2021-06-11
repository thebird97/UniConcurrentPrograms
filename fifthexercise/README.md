# Readme file for fifth exercise

Wait-notify
<p>
The `wait()` and `notify()` methods are designed to provide a mechanism to allow a thread to block until a specific condition is met. For this I assume you're wanting to write a blocking queue implementation, where you have some fixed size backing-store of elements.

The first thing you have to do is to identify the conditions that you want the methods to wait for. In this case, you will want the put() method to block until there is free space in the store, and you will want the take() method to block until there is some element to return.

### First task: 
* Simple example
### Second task
* Vaccination task


