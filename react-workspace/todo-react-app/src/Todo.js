import React from "react";
import { ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction, IconButton } from "@material-ui/core";
import { DeleteOutline } from "@material-ui/icons";
import { toHaveAccessibleDescription } from "@testing-library/jest-dom/matchers";

class Todo  extends React.Component {
    constructor(props){
        super(props); //props오브젝트 초기화, 여기서 super는 react.component의 constructor
                      //super가 호출되기 전까지는 this를 사용할 수 없다.
        this.state =  { item: props.item, readOnly : true }; //state는 리액트가 관리하는 오브젝트
        //자바스크럽트 내에서 변경한 변수의 값을 HTML에 다시 렌더링 하기 위함
        this.delete = props.delete;
        this.update = props.update; //update를 this.update에 할당

    }

    deleteEventHandler = () => {
        this.delete(this.state.item);
    }

    offReadOnlyMode = () => {
        console.log("Event!", this.state.readOnly);
        this.setState({readOnly:false},() =>{
            console.log("ReadOnly? ", this.state.readOnly)
        })
    }

    enterkeyEventHandler = (e) =>{
        if(e.key === "Enter"){
            this.setState({readOnly:true});
            this.update(this.state.item);//엔터 누르면 저장
        }
    }

    editEvanetHandler = (e) =>{
        const thisItem =  this.state.item;
        thisItem.title = e.target.value; //e.target.value로 input값 받아옴
        this.setState({ item:thisItem})
    }
    
    //체크박스 done의 true->f 바꿔줌
    checkboxEventHandler = (e) => {
        const thisItem = this.state.item;
        thisItem.done = !thisItem.done;
        this.setState({ item:thisItem});
        this.update(this.state.item);//체크박스 누르면 저장
    }

    render() {
        const item = this.state.item;
        return(
            <ListItem>
                <Checkbox checked={item.done} onChange={this.checkboxEventHandler} />
                <ListItemText>
                    <InputBase
                        inputProps={{
                            "aria-label": "naked", 
                            readOnly : this.state.readOnly,
                            }}
                        onClick={this.offReadOnlyMode}
                        onKeyPress={this.enterkeyEventHandler}
                        onChange={this.editEvanetHandler}
                        type="text"
                        id={item.id} // 각 리스트를 구분하려고 id를 연결
                        name={item.id} // 각 리스트를 구분하려고 id를 연결
                        value={item.title}
                        multiline={true}
                        fullWidth={true}
                        />
                </ListItemText>
                <ListItemSecondaryAction>
                    <IconButton 
                        aria-label="Delete Todo"
                        onClick={this.deleteEventHandler}>
                        <DeleteOutline></DeleteOutline>
                    </IconButton>
                </ListItemSecondaryAction>
            </ListItem>
        );
    }
}

export default Todo;
