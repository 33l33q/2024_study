import logo from './logo.svg';
import React, { Component } from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import { Paper, List, Container, responsiveFontSizes } from '@material-ui/core';
import './App.css';
import {call} from "./service/ApiService"


class App extends React.Component {

  componentDidMount() {
    call("/todo","GET",null).then((response)=>
      this.setState({ items: response.data })
    );
  }

  constructor(props){
    super(props);
   this.state= { items : []
       /* items : [ {}
       { id : 0, title : "Hello World 1", done : true} ,
        { id : 1, title : "Hello World 2", done : false} 
      ], */
    };
  }

  //1 함수추가
  add = (item) => {

    call("/todo","POST",item).then((response)=>
      this.setState({ items: response.data })
    );
/*
    const thisItems = this.state.items;
    item.id = "ID-"+thisItems.length; //key를 위한 id 추가
    item.doen = false; //done 초기화
    thisItems.push(item);//리스트에 아이템 추가
    this.setState({item:thisItems}); //업데이트는 반드시 this.setState로 해야됨
    
    console.log("items : ", this.state.items);*/
  }

    //delete 함수 추가
  delete = (item) =>{
    
    call("/todo","DELETE",item).then((response)=>
      this.setState({items:response.data})
    );
   /*   const thisItems = this.state.items;
      console.log("Before update Items : ", this.state.items);
      
      const newItems = thisItems.filter(e => e.id !== item.id ); 
      //매개변수로 불러온 id와 실제 id가 같지않은거만 필터링
      
      this.setState({items:newItems}, () => {
        //디버깅 콜백
        console.log("update Items : ", this.state.items)
      });*/
  }

  update = (item)=>{
    call("/todo", "PUT", item).then((response)=>
      this.setState({item: response.data })
    );
  }

  render(){

    var todoItems = this.state.items.length > 0 && (
      <Paper style={{ margin:16}}>
        <List>
          {this.state.items.map((item, idx)=>(
            // delete 함수 연결
            <Todo item={item} key={item.id} delete={this.delete} update={this.update} />
          ))}
        </List>
      </Paper>
    );
  
    // 2 함수연결
    return(
      <div className="App">
        <Container maxWidth="md">
          <AddTodo add = {this.add}></AddTodo>
          <div className="TodoList">{todoItems}</div>
        </Container>
       </div>
    );
  }
}

export default App;
