<?php

header('Content-Type:multipart/form-data');
header('Access-Control-Allow-Methods: GET,POST,DELETE.PUT');
header('Access-Control-Allow-Origin: *');
    
    if (isset($_POST['CMD'])){
        require_once('MyConn.php');
		switch($_POST['CMD']) {
		
			case 'Test':
				$response['error'] = false;
				$response['message'] = 'Berhasil test';
				echo json_encode($response);
			
			break;
			
			case 'tbl_user':
				$result = array();
				$sql = "SELECT * FROM tbl_user";
				$query = mysqli_query($my_conn,$sql);
				
				if($query){
					while($row = mysqli_fetch_assoc($query)){
						$result[] = $row;
					}
					echo json_encode($result);
				}else{
					$return["error"] = true;
					$return["message"] = "Database error".$sql;
				
					// tell browser that its a json data
					echo json_encode($return);
				}
				break;

			case 'login_user':
				$result = array();
				$username = $_POST['username'];
				$password = $_POST['password'];

				$sql = "SELECT * FROM tbl_user WHERE username = '$username' AND password = '$password'";
				$query = mysqli_query($my_conn,$sql);
			
				if(mysqli_num_rows($query) == 0){
					echo json_encode("Wrong username or password");
				} else {
					echo json_encode("Login Successfull");
				}
				break;
			
			case 'register_user' :
				$result = array();
				$username = $_POST['username'];
				$fullname = $_POST['fullname'];
				$email = $_POST['email'];
				$password = $_POST['password'];
				
				$sql = "INSERT INTO tbl_user (fullname, username, email, password) VALUES ('$fullname','$username','$email','$password')";

				if(mysqli_query($my_conn, $sql)) {
					echo json_encode("Register Successfull!!");
				} else {
					echo "Error";
				}

				break;
			default:
				$response['error'] = true;
				$response['message'] = '505';
				echo json_encode($response);
		}
	}
?>