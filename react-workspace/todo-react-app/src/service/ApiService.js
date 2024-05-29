import { API_BASE_URL } from "../app-config";

export function call(api, method, request){
   let options = {
        headers:new Headers({
            "Content-Type" : "application/json",
        }),
        url : API_BASE_URL + api,
        method,
    };

    if(request){
        //get method
        options.body = JSON.stringify(request);
    }

    return fetch(options.url, options).then((response) =>
        response.json().then((json)=>{
            if (!response.ok){
                //response.ok가 true면 정상적인 응답 받은거, 아니면 에러응답 받은거!
                return Promise.reject(json)
            }
            return json;
        })
    );
}