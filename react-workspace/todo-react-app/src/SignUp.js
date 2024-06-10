import React from "react";
import {Button, TextField, Link, Grid, Container, Typography } from "@material-ui/core";

import { signup } from "./service/ApiService";
import { Class } from "@material-ui/icons";


function SignUp(){
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const username = data.get("username");
        const email = data.get("email");
        const password = data.get("password");
        signup({ email: email, username: username, password: password }).then(
          (response) => {
            // 계정 생성 성공 시 login페이지로 리디렉트
            window.location.href = "/login";
          }
        );
    }

    return (
        <Container component="main" maxWidth="xs" style={{maringTop : "8%"}}>
            <form noValidate onSubmit={handleSubmit}>
                <Grid Container spacing={2}>
                    <Grid item xs={12}>
                        <Typography component="h1" variant="h5">
                            계정 생성
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField autoComplete="fname" name="username" id="username"
                                    variant="outlined" required fullWidth label="사용자 이름" autoFocus>
                        </TextField>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField variant="outlined" required fullWidth id="email" label="이메일 주소"
                                    name="email" autoComplete="email"></TextField>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField variant="outlined" required fullWidth name="password" type="password"
                                    id="password" autoComplete="current-password" label="비밀번호"></TextField>
                    </Grid>
                    <Grid item xs={12}>
                        <Button type="submit" fullWidth variant="contained" color="primary">계정 생성</Button>
                    </Grid>
                </Grid>
                <Grid container justify="flex-end">
                    <Grid item>
                        <Link href="/login" variant="body2">
                           이미 계정이 있습니까? 여기서 로그인 하십시오.
                        </Link>
                    </Grid>
                </Grid>
            </form>
        </Container>
    );
};

export default SignUp;