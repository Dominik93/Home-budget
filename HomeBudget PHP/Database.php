
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
    
    public function lastID(){
        return $this->link->insert_id;
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
    public function insertUser($token, $name){ 
        $query = "insert into user (token, name, savings) values ('". $this->clear($token) ."', '". $this->clear($name) ."', 0);";
        if(mysqli_query($this->link, $query)){
            $query = "select id from user where token = '". $this->clear($token) ."'";
            $row = mysqli_fetch_array(mysqli_query($this->link, $query));
            return $this->insertSettings($row['id']);
        }
        else{
            return $this->getError();
        }
    }
        
    /*
        TODO:
    */
    private function insertSettings($id){
        $query = 'INSERT INTO settings(id_user, auto_delete, auto_savings, auto_local_save)'
                . ' VALUES ("'. $this->clear($id) .'", 0, 0, 0)';
        if(mysqli_query($this->link, $query))
            return true;
        else 
            return false;
    } 
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function insertCategory($id, $name, $type){
        echo $this->clear($name);
        $query = "insert into category (id_user, name, type)"
                . " values ('". $this->clear($id) ."', '".  $this->clear($name) ."', '". $this->clear($type) ."');";
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
    public function insertElement($category_id, $name, $value, $const, $date = null){
        if($date == null){
          $query = "insert into element (id_category, name, value, const)"
                . " values ('". $this->clear($category_id) ."', '". $this->clear($name) ."', '". $this->clear($value) ."','". $this->clear($const) ."');";
        }
        else{
        $query = "insert into element (id_category, name, value, const, date)"
                . " values ('". $this->clear($category_id) ."', '". $this->clear($name) ."', '". $this->clear($value) ."','". $this->clear($const) ."','". $this->clear($date) ."');";
        }
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
    
    public function deleteUser($id){
        if($this->deleteElements($id) !== true){
            echo '1';
            return $this->getError();
        }
        if($this->deleteCategories($id) !== true){
            echo '2';
            return $this->getError();
        }
        if($this->deleteSettings($id) !== true){
            echo '3';
            return $this->getError();
        }
        $query = 'delete from user '
                . 'where id like "'.$this->clear($id).'"';
        
        if(mysqli_query($this->link, $query) === true)
            return true;
        
        else 
            return $this->getError();
    }
    
    /*
     * return value boolean or string
     * if query successed
     * true
     * -----------
     * if query failed
     * a string that describes the error
     */
    public function deleteCategory($id){
        $query = 'select * from element '
                . 'join category on category.id = element.id_category '
                . 'where category.id = '. $id .';';
        $result = mysqli_query($this->link, $query);
        while($row = mysqli_fetch_array($result)){
            $this->deleteElement($id_user, $row['id']);
        }
        $query = 'delete from category '
                . 'where category.id = "'. $this->clear($id) .'" ;';
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
    
    public function deleteCategories($id_user){
        $query = 'delete from category '
                . 'where category.id_user = '
                . '(select id from user where id = "'.$this->clear($id_user).'");';
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
    public function deleteElement($id){
        $query = 'delete from element '
                . 'where id = "'. $this->clear($id) .'";';
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
    
    public function deleteElements($id_user){
        $query = 'delete FROM element
                    where element.id_category = 
                    (select id from category where id_user = 
                        (select id from user where id = "'.$this->clear($id_user).'"))';
        if(mysqli_query($this->link, $query)){
           return true;
        }
        else{
            return $this->getError();
        }
    }
      
    public function deleteSettings($id_user){
        $query = 'delete from settings '
                . 'where settings.id_user = '
                . '(select id from user where id = "'.$this->clear($id_user).'");';
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
    public function updateCategory($id, $name){
        $query = 'update category '
                . 'set name="'.$this->clear($name).'" '
                . 'where id="'.$this->clear($id).'";';
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
    public function updateElement($id, $name, $value, $const, $date){
        $query = 'update element '
                . 'set name = "'.$this->clear($name).'", '
                . 'value = "'.$this->clear($value).'",'
                . 'const = "'.$this->clear($const).'", '
                . 'date = "'.$this->clear($date).'"'
                . 'where id = "'. $this->clear($id) .'";';
        if(mysqli_query($this->link, $query)){
           return true;
        }else{
            return $this->getError();
        }
    }
    
    public function getUser($token){
        $query = 'select user.*, settings.auto_delete, settings.auto_savings, settings.auto_local_save'
                . ' from user join settings on settings.id_user = user.id '
                . 'where user.token like "'. $this->clear($token) .'";';
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
    public function getCategories($id_user, $type){
        $query = 'select category.* from category '
                . 'join user on user.id = category.id_user '
                . 'where category.type like "'.$this->clear($type).'" and '
                . 'user.id like "'. $this->clear($id_user) .'";';
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
    public function getElements($id_category){
        $query = 'select element.* from element '
                . 'join category on category.id = element.id_category '
                . 'join user on user.id = category.id_user '
                . 'where category.id like "'.$this->clear($id_category);
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
        $query = "select * from user where token = '". $this->clear($token)."';";
        $result = mysqli_query($this->link, $query);
        if(!$result){
            return $this->getError();
        }
        if(mysqli_num_rows($result) > 0){
            return true;
        }
        return false;
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
}
