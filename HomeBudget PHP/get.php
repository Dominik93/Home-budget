<?php

include "Database.php";

$mysql = new Mysql("mysql.hostinger.pl", "u906935981_pz", "Aq12wS", "u906935981_pz");

$tab_bool[1] = "true";
$tab_bool[0] = "false";

if(isset($_POST['get_user'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->getUser($_POST['get_user']);
    if($result === false){
        $response['response_value'] = 0;
        $response['response_message'] = $mysql->getError();
    }
    else{
        if(mysqli_num_rows($result) >= 0){
            $row = mysqli_fetch_array($result);
            $response['response_value'] = 1;
            $response['response_message']= 'GET_SUCCESS';
            $response['response_id'] = $row['id'];
            $response['response_savings'] = $row['savings'];
            $response['response_auto_save'] = $tab_bool[$row['auto_savings']];
            $response['response_auto_delete'] = $tab_bool[$row['auto_delete']];
            $response['response_auto_local_save'] = $tab_bool[$row['auto_local_save']];            
        }        
        else{
            $response['response_value'] = 2;
            $response['response_message'] = 'GET_SUCCESS_NULL';
        }
    }
    echo json_encode($response);
    $mysql->close();
}

/*
 * return value array
 * valuse = 0
 * message = error while execute query
 * -----------------
 * valuse = 1
 * message = GET_SUCCESS
 * array = array
 * -----------------
 * valuse = 2
 * message = GET_SUCCESS_NULL
 */
if(isset($_POST['get_category']) && isset($_POST['get_category_type'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->getCategories($_POST['get_category'], $_POST['get_category_type']);
    if($result === false){
        $response['response_value'] = 0;
        $response['response_message'] = $mysql->getError();
    }
    else{
        if(mysqli_num_rows($result) >= 0){
            $temp_id = array();
            $temp_id_user = array();
            $temp_name = array();
            $i = 0;
            while($row = mysqli_fetch_array($result)){
                $temp_id['id['.$i.']'] = $row['id'];
                $temp_id_user['id_user['.$i.']'] = $row['id_user'];
                $temp_name['name['.$i++.']'] = $row['name'];
            }
        
            $response['response_value'] = 1;
            $response['response_message'] = 'GET_SUCCESS';
            $response['response_array_id'] = $temp_id;
            $response['response_array_id_user'] = $temp_id;
            $response['response_array_name'] = $temp_name;
        }        
        else{
            $response['response_value'] = 2;
            $response['response_message'] = 'GET_SUCCESS_NULL';
        }
    }
    echo json_encode($response);
    $mysql->close();
}

if(isset($_POST['get_element'])){
    $response = array();
    $mysql->connect();
    $result = $mysql->getElements($_POST['get_element']);
    if($result === false){
        $response['response_value'] = 0;
        $response['response_message'] = $mysql->getError();
    }
    else{
        if(mysqli_num_rows($result) > 0){
            $temp_id = array();
            $temp_id_category = array();
            $temp_name = array();
            $temp_value = array();
            $temp_const = array();
            $temp_date = array();
            $i = 0;
            while($row = mysqli_fetch_array($result)){
                $temp_id['id['.$i.']'] = $row['id'];
                $temp_id_category['id_category['.$i.']'] = $row['id_category'];
                $temp_name['name['.$i.']'] = $row['name'];
                $temp_const['const['.$i.']'] = $tab_bool[$row['const']];
                $temp_date['date['.$i.']'] = $row['date'];
                $temp_value['value['.$i++.']'] = $row['value'];
            }
            $response['response_value'] = 1;
            $response['response_message'] = 'GET_SUCCESS';
            $response['response_array_id'] = $temp_id;
            $response['response_array_id_category'] = $temp_id_category;
            $response['response_array_name'] = $temp_name;
            $response['response_array_element_value'] = $temp_value;
            $response['response_array_const'] = $temp_const;
            $response['response_array_date'] = $temp_date;
        }        
        else{
            $response['response_value'] = 2;
            $response['response_message'] = 'GET_SUCCESS_NULL';
        }
    }
    echo json_encode($response);
    $mysql->close();
}
