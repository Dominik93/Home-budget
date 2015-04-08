
<?php
class Mysql{
    private $host;
    private $user;
    private $password;
    private $name;
    private $link;
    
    public function __construct($h, $u, $p, $d){
        $this->host = $h;
        $this->user = $u;
        $this->password = $p;
        $this->name = $d;
        date_default_timezone_set('Europe/Warsaw');
    }
    
    public function connect(){
        $this->link = new mysqli($this->host, $this->user, $this->password, $this->name);
        $this->link->set_charset("utf8");
    }
    
    public function getError(){
        return mysqli_error($this->link);
    }
    
    public function close(){
        mysqli_close($this->link);
    }
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function insertToken($token){ 
        $query = "insert into user (id) values ('". $this->clear($token) ."');";
        if(mysqli_query($this->link, $query)){
            return true;
        }
        else{
            return $this->getError();
        }
    }
    
    public function insertSettings($token){
        $query = 'INSERT INTO settings(id_user, auto_delete, auto_savings) VALUES ("'. $this->clear($token) .'", 0, 0)';
        mysqli_query($this->link, $query);
    } 
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function insertCategory($token, $name, $type){
        echo $this->clear($name);
        $query = "insert into category (id_user, name, type)"
                . " values ('". $this->clear($token) ."', '".  $this->clear($name) ."', '". $this->clear($type) ."');";
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function insertElement($category, $name, $value, $const, $date = null){
        if($date == null){
          $query = "insert into element (id_category, name, value, const)"
                . " values ('". $this->clear($category) ."', '". $this->clear($name) ."', '". $this->clear($value) ."','". $this->clear($const) ."');";
        }
        else{
        $query = "insert into element (id_category, name, value, const, date)"
                . " values ('". $this->clear($category) ."', '". $this->clear($name) ."', '". $this->clear($value) ."','". $this->clear($const) ."','". $this->clear($date) ."');";
        }
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function deleteToken($token){
        $query = "delete from user where id like '". $this->clear($token) ."');";
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function deleteCategory($token, $id){
        $query = 'select * from element join category on category.id = element.id_category where category.id = '. $id .';';
        $result = mysqli_query($this->link, $query);
        while($row = mysqli_fetch_array($result)){
            $this->deleteElement($token, $row['id']);
        }
        $query = 'delete from category '
                . 'join user on user.id = category.id_user '
                . 'where category.id = "'. $this->clear($id) .'" and user.id = "'.$this->clear($token).'";';
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function deleteElement($token, $id){
        $query = 'delete from element '
                . 'join category on category.id = element.id_category '
                . 'join user on user.id = category.id_user '
                . 'where id = "'. $this->clear($id) .'" and user.id = "'.$this->clear($token).'";';
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
      
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function updateCategory($token, $id, $name){
        $query = 'update category '
                . 'join user on user.id = category.id_user '
                . 'set name="'.$this->clear($name).'" '
                . 'where category.id="'.$this->clear($id).'" and user.id="'.$this->clear($token).'";';
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function updateElemen($token, $id, $name, $value, $const, $date){
        $query = 'update element '
                . 'join category on category.id = element.id_category '
                . 'join user on user.id = category.id_user '
                . 'set element.name = "'.$this->clear($name).'", element.value = "'.$this->clear($value).'",'
                . ' element.const = "'.$this->clear($const).'", element.date = "'.$this->clear($date).'"'
                . 'where element.id = "'. $this->clear($id) .'" and user.id = "'.$this->clear($token).'" ;';
        if(mysqli_query($this->link, $query)){
           return true;
        }else{
            return $this->getError();
        }
    }
    
    public function getUser($token){
        $query = 'select user.*, settings.auto_delete, settings.auto_savings from user join settings on settings.id_user = user.id '
                . 'where user.id like "'. $this->clear($token) .'";';
        return mysqli_query($this->link, $query);
    }
            
    /*
     * return value mysqli_result object or string
     * if query successed
     * mysqli_result object
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function getCategory($token, $type){
        $query = 'select category.* from category '
                . 'join user on user.id = category.id_user '
                . 'where category.type like "'.$this->clear($type).'" and '
                . 'user.id like "'. $this->clear($token) .'";';
        return mysqli_query($this->link, $query);
    }
    
    /*
     * return value mysqli_result object or string
     * if query successed
     * mysqli_result object
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function getElement($token, $id){
        $query = 'select element.* from element '
                . 'join category on category.id = element.id_category '
                . 'join user on user.id = category.id_user '
                . 'where category.id like "'.$this->clear($id).'" and '
                . 'user.id like "'.  $this->clear($token) .'";';
        return mysqli_query($this->link, $query);
    }
    
    
    /*
     * return value boolean or string
     * if query successed and query return row
     * true
     * -----------
     * if query successed and query dont return row
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function tokenExist($token){
        $query = "select * from user where id = '". $this->clear($token)."';";
        $result = mysqli_query($this->link, $query);
        if(!$result){
            return $this->getError();
        }
        if(mysqli_num_rows($result) > 0){
            return true;
        }
        return false;
    }
    
    public function deleteUser($token){
        $query = 'select element.* from element '
                . 'join category on category.id = element.id_category '
                . 'join user on user.id = category.id_user '
                . 'where user.id like "'.$this->clear($token).'"';
        $result = mysqli_query($this->link, $query);
        while($row = mysqli_fetch_array($result)){
            $query = 'delete from element where id = '. $row['id'].';';
            mysqli_query($this->link, $query);
        }
        $query = 'delete from category where id_user like (select id from user where id like "'.$this->clear($token).'")';
        
        mysqli_query($this->link, $query);
        $query = 'delete from user '
                . 'where user.id like "'.$this->clear($token).'"';
        mysqli_query($this->link, $query);
        
        $query = 'delete from settings '
                . 'where settings.id_user like "'.$this->clear($token).'"';
        mysqli_query($this->link, $query);
        return true;
    }
    
    
    private function clear($text){
        if(empty($text)){
            return false;
        }
        if(get_magic_quotes_gpc()) {
            $text = stripslashes($text);
        }
        return (mysqli_real_escape_string($this->link, htmlspecialchars(trim($text))));
    }

    
    public function testclear($text){
        if(empty($text)){
            return false;
        }
        if(get_magic_quotes_gpc()) {
            $text = stripslashes($text);
        }
        return (htmlspecialchars(trim($text)));
    }
}
