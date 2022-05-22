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
        loginToken(credentials)
            .then(response => {
                localStorage.setItem("tokens", JSON.stringify(response))
                sendLogin(credentials)
                    .then(customerData => {
                        localStorage.setItem("admin-info", JSON.stringify(customerData))
                        navigate("/admin/home")
                    })
                    .catch(() => {
                        setError("Invalid credentials")
                    })
            })
            .catch(() => {
                setError("Invalid credentials")
            })
        event.preventDefault()
    }

    return (
        <div>
            <h1>Login page</h1>
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

async function loginToken(credentials) {
    const params = new URLSearchParams()
    params.append('username', credentials.email)
    params.append('password', credentials.password)
    const response = await axios(
        {
            method: 'POST',
            url: path + "login",
            params: params
        });
    return await response.data;
}

async function sendLogin(credentials) {
    const response = await axios(
        {
            method: 'POST',
            url: path + 'admin/auth',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            data: JSON.stringify(credentials),
        });
    return await response.data;
}

export default AdminLogin;