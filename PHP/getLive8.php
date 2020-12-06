<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');


    $stmt = $con->prepare('select * from board where line = 8');
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $sub = array();

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);

            array_push($sub,
                array('line'=>$line,
                'train'=>$train,
                'data'=>$data,
	    'num'=>$num,
	    'title'=>$title,
	    'time'=>$time
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("실시간데이터"=>$sub), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }

?>
