package statemachine;

public class State {
    public String stateName;
    protected Statemachine statemachine;
    public State(String stateName, Statemachine statemachine){
        this.stateName = stateName;
        this.statemachine = statemachine;
    }

    public void Init(){
        statemachine.currentState = this;
    }

    public void Update(){

    }

    public void Exit(){
        statemachine.currentState = null;
    }
}
