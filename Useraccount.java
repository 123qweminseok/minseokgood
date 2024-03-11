package com.minseok.hongdroid1;

public class Useraccount{

public Useraccount(){

}

public String getIdToken(){ return idToken;}

public void setIdToken(String idToken){

    this.idToken= idToken;

}

private String idToken;

public String getEmailId(){
    return emailId;}

public void setEmailId(String emailId){

    this.emailId=emailId;}



    private String emailId;

public String getPassword() {   return password;}

public void setPassword(String password){this.password=password;}
private String password;


}
//이 클래스는 사용자의 계정 정보를 나타내는 데이터 모델입니다.
//사용자의 ID 토큰, 이메일 주소 및 비밀번호를 저장하는 데 사용됩니다.
//클래스에는 데이터를 설정하고 반환하기 위한 getter 및 setter 메서드가 있습니다.