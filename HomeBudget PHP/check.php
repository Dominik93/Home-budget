<?php
include "Database.php";

$mysql = new Mysql("mysql.hostinger.pl", "u906935981_pz", "Aq12wS", "u906935981_pz");

/*
 * return value
 * valuse = 0
 * message = error while execute query
 * -----------------
 * valuse = 1
 * message = EXIST
 * -----------------
 * valuse = 2
 * message = NOT_EXIST
 */
if(isset($_POST['check_token'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->tokenExist($_POST['check_token']);
    if($result === true){
        $response['response_value'] = 1;
        $response['response_message'] = 'EXIST';
    }
    elseif($result === false){
        $response['response_value'] = 2;
        $response['response_message'] = 'NOT_EXIST';
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    echo json_encode($response);
    $mysql->close();
}