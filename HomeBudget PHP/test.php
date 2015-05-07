
<?php

echo  '<form action="insert.php" method="post">
        insert Token:<br>
        <input type="text" name="insert_token" placeholder="token">
        <input type="text" name="insert_token_name" placeholder="name">
        <input type="submit" value="Submit">
        </form> ';

echo  '<form action="delete.php" method="post">
        delete user:<br>
        <input type="text" name="delete_user" placeholder="token">
        <input type="submit" value="Submit">
        </form> ';

echo  '<form action="insert.php" method="post">
        insert category:<br>
        <input type="text" name="insert_category" placeholder="token">
        <input type="text" name="insert_category_name" placeholder="name">
        <input type="text" name="insert_category_type" placeholder="type">
        <input type="submit" value="Submit">
        </form> ';

echo  '<form action="insert.php" method="post">
        insert element:<br>
        <input type="text" name="insert_element_id_category" placeholder="id_category">
        <input type="text" name="insert_element_name" placeholder="name">
        <input type="text" name="insert_element_value" placeholder="value">
        <input type="text" name="insert_element_const" placeholder="const">
        <input type="text" name="insert_element_date" placeholder="date">
        <input type="submit" value="Submit">
        </form> ';

echo  '<form action="check.php" method="post">
        check Token:<br>
        <input type="text" name="check_token" placeholder="token">
        <input type="submit" value="Submit">
        </form> ';

echo  '<form action="get.php" method="post">
        get user:<br>
        <input type="text" name="get_user" placeholder="token">
        <input type="submit" value="Submit">
        </form> ';

echo  '<form action="get.php" method="post">
        get category:<br>
        <input type="text" name="get_category" placeholder="token">
        <input type="text" name="get_category_type" placeholder="type">
        <input type="submit" value="Submit">
        </form> ';

echo  '<form action="get.php" method="post">
        get element:<br>
        <input type="text" name="get_element" placeholder="token">
        <input type="text" name="get_category_id" placeholder="category_id">
        <input type="submit" value="Submit">
        </form> ';

echo  '<form action="update.php" method="post">
        update category:<br>
        <input type="text" name="update_category" placeholder="id">
        <input type="text" name="update_category_name" placeholder="name">
        <input type="submit" value="Submit">
        </form> ';

echo  '<form action="update.php" method="post">
        update element:<br>
        <input type="text" name="update_element_id" placeholder="id">
        <input type="text" name="update_element_name" placeholder="name">
        <input type="text" name="update_element_value" placeholder="value">
        <input type="text" name="update_element_const" placeholder="const">
        <input type="text" name="update_element_date" placeholder="date">
        <input type="submit" value="Submit">
        </form> ';
