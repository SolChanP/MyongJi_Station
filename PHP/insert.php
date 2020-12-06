<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $line=$_POST['line'];
        $train=$_POST['train'];
$data=$_POST['data'];
$title=$_POST['title'];
$time=$_POST['time'];


        if(!isset($errMSG)) // 모두 입력 되었다면
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 board 테이블에 저장합니다.
                $stmt = $con->prepare('INSERT INTO board(line, train, data, title, time) VALUES(:line, :train, :data, :title, :time)');
                $stmt->bindParam(':line', $line);
                $stmt->bindParam(':train', $train);
$stmt->bindParam(':data', $data);
$stmt->bindParam(':title', $title);
$stmt->bindParam(':time', $time);
	

                if($stmt->execute())
                {
                    $successMSG = "새로운 게시글을 추가하였습니다.";
                }
                else
                {
                    $errMSG = "게시글 추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
        }

    }

?>
