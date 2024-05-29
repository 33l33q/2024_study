import React from "react";
import { TextField, Paper, Button, Grid } from "@material-ui/core";

class AddTodo extends React.Component {
    constructor(props){
        super(props);
        this.state = {item : {title : "" }} ; //사용자의 입력을 저장할 오브젝트
        this.add = props.add; // props의 함수를 this.add에 연결
    }
    //1 함수작성
    // 사용자가 키를 하나 입력할때마다 작동함, 문자열을 자바스크립트 오브젝트에 저장함
    onInputChange = (e) => {
         //사용자가 input 필드에 입력하는 정보를 컴포넌트 내부에 임시로 저장하려고 상태변수 초기화
        var thisItem = this.state.item;
        thisItem.title = e.target.value;
        this.setState({item:thisItem});
        console.log(this.state.item.title);
    }

    // Add 함수 작성
    onButtonClick = () =>{
        this.state.item.title != '' && this.add(this.state.item); //add 함수 사용
        this.setState({item : {title : ""}});
    }

    enterkeyEventHandler = (e) =>{
        if(e.key === 'Enter'){
            this.onButtonClick();
        }
    }

    //2 함수연결
    render() {
        return(
            <paper style={{margin :16, padding:16}}>
                <Grid container>
                    <Grid xs={11} md={11} item style={{paddingRight:16}}>
                        <TextField 
                            placeholder=" Add Todo here " 
                            fullWidth
                            onChange={this.onInputChange}
                            value={this.state.item.title}
                            onKeyPress={this.enterkeyEventHandler}
                            />
                    </Grid>
                    <Grid xs={1} md={1} item>
                        <Button 
                            fullWidth 
                            color="secondary" 
                            variant="outlined"
                            onClick={this.onButtonClick} //Add 함수 연결
                            >
                            +
                        </Button>
                    </Grid>
                </Grid>
            </paper>
        );
    }
}

export default AddTodo;