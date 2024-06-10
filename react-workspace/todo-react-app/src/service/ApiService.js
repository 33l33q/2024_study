import { API_BASE_URL } from "../app-config";
const ACCESS_TOKEN = "ACCESS_TOKEN"; //모든 API 헤더의 엑세스 토큰을 추가하는 부분

export function call(api, method, request){
    let headers = new Headers({
        "Content-Type": "application/json",
    });
    
    //로컬 스토리지에서 ACCESS_TOKEN 가져오기
    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    if(accessToken && accessToken !== null){
        headers.append("Authorization", "Bearer " + accessToken);
    }
    
    let options = {
        headers,
        url: API_BASE_URL + api,
        method
    };
    
    if(request){
        //GET METHOD
        options.body = JSON.stringify(request);
    }
    return fetch(options.url, options).then((response) => {
        if (response.status === 200) {
          return response.json();
        } else if(response.status === 403) {
          window.location.href = "/login"; // redirect
        } else {
          new Error(response);
        }
      }).catch((error) => {
          console.log("http error");
          console.log(error);
        });
    
}

 //로그인하는 서비스 함수 작성
 export function signin(userDTO){
    return call("/auth/signin", "POST", userDTO)
    .then((response) =>{
        console.log("response :", response);
       // alert("로그인 토큰:" + response.token);
       if(response.token){
        //로컬 스토리지에 토큰 저장하기
        localStorage.setItem("ACCESS_TOKEN", response.token);
        //tokem이 존재하는 경우 Todo화면으로 리디렉트
        window.location.href = "/";
       }
    });
 }

    //로그아웃하는 서비스 함수
  export function signout(){
    localStorage.setItem(ACCESS_TOKEN, null);
    window.location.href = "/login";  
  }

  export function signup(userDTO){
    return call("/auth/signup","POST",userDTO);
  }