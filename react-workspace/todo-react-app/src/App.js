import logo from './logo.svg';
import React, { Component } from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import { Paper, List, Container, responsiveFontSizes, 
         Grid, Button, AppBar, Toolbar, Typography } from '@material-ui/core';
import './App.css';
import {call, signout} from "./service/ApiService"


class App extends React.Component {

  componentDidMount() {
    call("/todo","GET",null).then((response)=>
      this.setState({ items: response.data })
    );
  }

  constructor(props){
    super(props);
    this.state= { items : [] ,
                  loading :  true, /*로딩중임을 표현할 변수 추가 */

       /* items : [ {}
       { id : 0, title : "Hello World 1", done : true} ,
        { id : 1, title : "Hello World 2", done : false} 
      ], */
    };
  }

  componentDidMount(){
    call("/todo", "GET", null).then((response) => 
    this.setState({item:response.data, loading:false})
  );
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
  
    //navigationBar추가
    var navigationBar = (
      <AppBar position="static">
        <Toolbar>
          <Grid justify='space-between' container>
            <Grid item>
              <Typography variant='h6'>오늘의 할일</Typography>
            </Grid>
            <Grid>
              <Button color='inherit' onClick={signout}>
                로그아웃
              </Button>
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
    )

    /* 로딩 중이 아닐 때 렌더링 할 부분 */
    var todoListPage = (
      <div>
        {navigationBar}{/*네비게이션바 렌더링*/}
        <contaner maxWidth="md">
          <AddTodo add={this.add}/>
          <div className='TodoList'>{todoItems}</div>
        </contaner>
      </div>
    )
    /*로딩 중일떄 렌더링 할 부분 */
    var loadingPage = <h1> 로딩중.. </h1>
    var content = loadingPage;
    if(!this.state.loading){
      /*로딩중이 아니면! todoListPage를 선택*/
      content = todoListPage;
    }

    /* 선택한 Content 렌더링 */

    return <div className='App'>{content}</div>
    /*2 함수연결
    return(
      <div className="App">
        {navigationBar} 
        <Container maxWidth="md">
          <AddTodo add = {this.add}></AddTodo>
          <div className="TodoList">{todoItems}</div>
        </Container>
       </div>
    ); */
  }
}

export default App;
