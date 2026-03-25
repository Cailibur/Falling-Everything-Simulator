package statemachine;

public class Statemachine {
    public State currentState; 
    public Statemachine(){
    }

    public void ChangeState(State newState){
        if(this.currentState != null) currentState.Exit();
        newState.Init();
    }
}
