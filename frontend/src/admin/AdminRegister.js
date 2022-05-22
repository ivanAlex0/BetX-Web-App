import {useState} from "react";
import {Button, Form} from "react-bootstrap";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function AdminLogin() {
    localStorage.clear()

    const navigate = useNavigate()
    const [credentials, setCredentials] = useState()
    const [error, setError] = useState()

    function handleChange(event) {
        let {name, value} = event.target
        setCredentials(prevState => {
            return {
                ...prevState,
                [name]: value
            }
        })
    }

    function handleSubmit(event) {
        sendLogin(credentials)
            .then(customerData => {
                localStorage.setItem("admin-info", JSON.stringify(customerData))
                navigate("/admin/login")
            })
            .catch((error) => {
                setError(error.response.data.message)
            })
        event.preventDefault()
    }

    return (
        <div>
            <h1>Admin Register Page</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group className={'mb-3'}>
                    <Form.Label>Email address</Form.Label>
                    <Form.Control
                        name={'email'}
                        type={'email'}
                        placeholder={'Enter email...'}
                        onChange={handleChange}/>
                </Form.Group>

                <Form.Group className={'mb-3'}>
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        name={'password'}
                        type={'password'}
                        placeholder={'Enter password...'}
                        onChange={handleChange}/>
                </Form.Group>

                <h1 style={{color: 'red', justifyContent: 'center', display: 'flex'}}>
                    {error}
                </h1>

                <Button variant="success" type="submit" style={{width: 400}}>
                    Login
                </Button>
            </Form>
        </div>
    );
}

const path = 'http://localhost:8080/';

async function sendLogin(credentials) {
    const response = await axios(
        {
            method: 'POST',
            url: path + 'admin/save',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            data: JSON.stringify(credentials),
        });
    return await response.data;
}

export default AdminLogin;