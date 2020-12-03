<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $train=$_POST['train'];
        $data=$_POST['data'];
        $phone=$_POST['phone'];

        if(!isset($errMSG)) // 모두 입력 되었다ㅕㄴ
        {
            try{
                $stmt = $con->prepare('INSERT INTO claim(train, data, phone) VALUES(:train, :data, :phone)');
                $stmt->bindParam(':train', $train);
                $stmt->bindParam(':data', $data);
                $stmt->bindParam(':phone', $phone);


                if($stmt->execute())
                {
                    $successMSG = "민원을 성공적으로 보냈습니다..";
                }
                else
                {
                    $errMSG = "에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
        }

    }

?>
