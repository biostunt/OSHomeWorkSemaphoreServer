class Semaphore {

    private int onQueue = 0;

    public Semaphore(){}
    public synchronized void  take(){
        onQueue = 1;
        while(onQueue > 1){
            try{
                wait();
            } catch (Exception e){}
        }
    }
    public synchronized void release() {
        onQueue = 0;
        this.notify();
    }
}
