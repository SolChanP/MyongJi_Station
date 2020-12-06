<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $num=$_POST['num'];
        $cmtData=$_POST['cmtData'];
        $time=$_POST['time'];

        if(!isset($errMSG)) // 모두 입력 되었다면
        {
            try{
                $stmt = $con->prepare('INSERT INTO comment(num, cmtData, time) VALUES(:num, :cmtData, :time)');
                $stmt->bindParam(':num', $num);
                $stmt->bindParam(':cmtData', $cmtData);
                $stmt->bindParam(':time', $time);


                if($stmt->execute())
                {
                    $successMSG = "새로운 댓글을 추가하였습니다.";
                }
                else
                {
                    $errMSG = "댓글 추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
        }

    }

?>
