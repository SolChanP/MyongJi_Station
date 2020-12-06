<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android)
    {
      $num=$_POST['num'];

    $stmt = $con->prepare('select * from comment where num = :num');
    $stmt->bindParam(':num', $num);
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $sub = array();

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);

            array_push($sub,
                array('cmtData'=>$cmtData,
                'time'=>$time)
		);
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("실시간데이터"=>$sub), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }
}
?>
