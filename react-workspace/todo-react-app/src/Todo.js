import React from "react";
import { ListItem, ListItemText, InputBase, Checkbox } from "@material-ui/core";

class Todo  extends React.Component {
    constructor(props){
        super(props); //props오브젝트 초기화, 여기서 super는 react.component의 constructor
                      //super가 호출되기 전까지는 this를 사용할 수 없다.
        this.state =  { item: props.item }; //state는 리액트가 관리하는 오브젝트
        //자바스크럽트 내에서 변경한 변수의 값을 HTML에 다시 렌더링 하기 위함

    }


    render() {
        const item = this.state.item;
        return(
            <ListItem>
                <Checkbox checked={item.done}/>
                <ListItemText>
                    <InputBase
                        inputProps={{"aria-label": "naked"}}
                        type="text"
                        id={item.id} //자바스크립트로 된 변수를 jsx에서 사용하려면 변수를 {}로 묶어주면 됨
                        name={item.id} //{자바스크립트코드} >> HTML 안에서도 자바스크립트를 이용할 수 있음
                        value={item.title}
                        multiline={true}
                        fullWidth={true}
                        />
                </ListItemText>
            </ListItem>
        );
    }

}

export default Todo;
