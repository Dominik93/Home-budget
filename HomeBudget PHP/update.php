<?php

include "Database.php";

$mysql = new Mysql("mysql.hostinger.pl", "u906935981_pz", "Aq12wS", "u906935981_pz");


if(isset($_POST['update_element']) && isset($_POST['update_element_name']) && isset($_POST['update_element_id'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->updateCategory($_POST['update_element'], $_POST['update_element_id'], $_POST['update_element_name']);
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
elseif(isset($_POST['update_element_detail']) && isset($_POST['update_element_detail_id']) && isset($_POST['update_element_detail_name']) && isset($_POST['update_element_detail_value'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->updateElement($_POST['update_element_detail'], $_POST['update_element_detail_id'], $_POST['update_element_detail_name'], $_POST['update_element_detail_value'], $_POST['update_element_detail_const'], $_POST['update_element_detail_date']);
    if($result === true){
        $response['response_value'] = 1;
        $response['response_message'] = "ELEMENT_DELATIL_MODIFY";
    }
    else{
        $response['response_value'] = 0;
        $response['response_message'] = $result;
    }
    $mysql->close();
    echo json_encode($response);
}