<?php

include "Database.php";

$mysql = new Mysql("mysql.hostinger.pl", "u906935981_pz", "Aq12wS", "u906935981_pz");

/*
 * return value array()
 * valuse = 0
 * message = error while execute query
 * -----------------
 * valuse = 1
 * message = TOKEN_ADDED
 */
if(isset($_POST['insert_user']) && isset($_POST['insert_user_name'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->insertUser($_POST['insert_user'], $_POST['insert_user_name']);
    if($result === true){
        $response['response_value'] = 1;
        $response['response_id_created_user'] = $mysql->lastID();
        $response['response_message'] = "TOKKEN_ADDED";
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    $mysql->close();
    echo json_encode($response);
}

/*
 * return value array()
 * valuse = 0
 * message = error while execute query
 * -----------------
 * valuse = 1
 * message = ELEMENT_ADDED
 */
if(isset($_POST['insert_category']) && isset($_POST['insert_category_name']) && isset($_POST['insert_category_type'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->insertCategory($_POST['insert_category'],
                                        $_POST['insert_category_name'],
                                        $_POST['insert_category_type']);
    if($result === true){
        $response['response_value'] = 1;
        $response['response_message'] = "CATEGORY_ADDED";
        $response['response_id_created_category'] = $mysql->lastID();
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    $mysql->close();
    echo json_encode($response);
}

/*
 * return value array()
 * valuse = 0
 * message = error while execute query
 * -----------------
 * valuse = 1
 * message = ELEMENT_ADDED
 */
if(isset($_POST['insert_element_id_category'])
        && isset($_POST['insert_element_name']) 
        && isset($_POST['insert_element_value'])
        && isset($_POST['insert_element_const'])){
    $response = array();
    $mysql->connect();
    if($result = $mysql->insertElement($_POST['insert_element_id_category'],
                                    $_POST['insert_element_name'], 
                                    $_POST['insert_element_value'], 
                                    $_POST['insert_element_const'], 
                                    $_POST['insert_element_date'])){
        $response['response_value'] = 1;
        $response['response_id_created_element'] = $mysql->lastID();
        $response['response_message'] = "ELEMENT_ADDED";
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    $mysql->close();
    echo json_encode($response);
}

if(isset($_POST['insert_settings'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->insertSettings($_POST['insert_settings']);
    if($result === true){
        $response['response_value'] = 1;
        $response['response_id_created_user'] = $mysql->lastID();
        $response['response_message'] = "SETTINGS_ADDED";
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    $mysql->close();
    echo json_encode($response);
}
