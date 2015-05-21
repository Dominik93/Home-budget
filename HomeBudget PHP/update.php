<?php

include "Database.php";

$mysql = new Mysql("mysql.hostinger.pl", "u906935981_pz", "Aq12wS", "u906935981_pz");

if(isset($_POST['update_category']) && isset($_POST['update_category_name'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->updateCategory($_POST['update_category'], $_POST['update_category_name']);
    if($result === true){
        $response['response_value'] = 1;
        $response['response_message'] = "CATEGORY_MODIFIED";
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    $mysql->close();
    echo json_encode($response);
}

if(isset($_POST['update_element_id']) &&
        isset($_POST['update_element_name']) &&
        isset($_POST['update_element_value'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->updateElement($_POST['update_element_id'],
                                    $_POST['update_element_name'],
                                    $_POST['update_element_value'],
                                    $_POST['update_element_const'],
                                    $_POST['update_element_date']);
    if($result === true){
        $response['response_value'] = 1;
        $response['response_message'] = "ELEMENT_MODIFY";
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    $mysql->close();
    echo json_encode($response);
}

if(isset($_POST['update_settings']) && isset($_POST['update_auto_savings'])
        && isset($_POST['update_auto_delete']) && isset($_POST['update_auto_local_save'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->updateSettings($_POST['update_settings'], $_POST['update_auto_savings']
            , $_POST['update_auto_delete'], $_POST['update_auto_local_save']);
    if($result === true){
        $response['response_value'] = 1;
        $response['response_message'] = "SETTINGS_MODIFIED";
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    $mysql->close();
    echo json_encode($response);
}

if(isset($_POST['update_user']) && isset($_POST['update_user_name']) && isset($_POST['update_user_savings'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->updateUser($_POST['update_user'], $_POST['update_user_name'], $_POST['update_user_savings']);
    if($result === true){
        $response['response_value'] = 1;
        $response['response_message'] = "USER_MODIFIED";
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    $mysql->close();
    echo json_encode($response);
}
