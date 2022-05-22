import {useEffect, useState} from "react";
import {Button, Form} from "react-bootstrap";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import Select from "react-select";

function CustomerRegister() {
    localStorage.clear()

    const navigate = useNavigate()
    const [customer, setCustomer] = useState({
        name: "",
        user: {
            email: "",
            password: ""
        },
        address: {
            number: "",
            street: "",
            city: "",
            addressCountry: {
                name: ""
            }
        }
    })
    const [address, setAddress] = useState()
    const [user, setUser] = useState()
    const [error, setError] = useState()
    const [countries, setCountries] = useState(null)

    useEffect(() => {
        if(countries == null){
            fetchCountries()
                .then(response => {
                    setCountries(response)
                })
                .catch(error => console.error(error))
        }

        setCustomer(prevState => {
            return {
                ...prevState,
                user: user,
                address: address
            }
        })
    }, [address, user])

    function handleChangeUser(event) {
        let {name, value} = event.target
        setUser(prevState => {
            return {
                ...prevState,
                [name]: value
            }
        })
    }

    function handleChangeCustomer(event) {
        let {name, value} = event.target
        setCustomer(prevState => {
            return {
                ...prevState,
                [name]: value
            }
        })
    }

    function handleChangeAddress(event) {
        let {name, value} = event.target
        setAddress(prevState => {
            return {
                ...prevState,
                [name]: value
            }
        })
    }

    function handleSelect(selected){
        setAddress(prevState => {
            return {
                ...prevState,
                addressCountry: {
                    name: selected.value
                }
            }
        })
    }

    function handleSubmit(event) {
        sendRegister(customer)
            .then(() => {
                navigate("/customer/login")
            })
            .catch(error => setError(error.response.data.message))
        event.preventDefault()
    }

    return (
        <div>
            <h1>Login page</h1>
            <Form onSubmit={handleSubmit}>
                <h3>User details</h3>
                <Form.Group className={'mb-3'}>
                    <Form.Label>Email address</Form.Label>
                    <Form.Control
                        name={'email'}
                        type={'email'}
                        placeholder={'Enter email...'}
                        onChange={handleChangeUser}/>
                </Form.Group>

                <Form.Group className={'mb-3'}>
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        name={'password'}
                        type={'password'}
                        placeholder={'Enter password...'}
                        onChange={handleChangeUser}/>
                </Form.Group>

                <Form.Group className={'mb-3'}>
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                        name={'name'}
                        type={'text'}
                        placeholder={'Enter name...'}
                        onChange={handleChangeCustomer}/>
                </Form.Group>

                <h3>Address</h3>

                <Form.Group className={'mb-3'}>
                    <Form.Label>Number</Form.Label>
                    <Form.Control
                        name={'number'}
                        type={'text'}
                        placeholder={'Enter number...'}
                        onChange={handleChangeAddress}/>
                </Form.Group>

                <Form.Group className={'mb-3'}>
                    <Form.Label>Street</Form.Label>
                    <Form.Control
                        name={'street'}
                        type={'text'}
                        placeholder={'Enter street...'}
                        onChange={handleChangeAddress}/>
                </Form.Group>

                <Form.Group className={'mb-3'}>
                    <Form.Label>City</Form.Label>
                    <Form.Control
                        name={'city'}
                        type={'text'}
                        placeholder={'Enter city...'}
                        onChange={handleChangeAddress}/>
                </Form.Group>

                <Select
                    options={countries?.map(country => {
                        return {
                            value: country.name,
                            label: country.name
                        }
                    })}
                    onChange={handleSelect}
                >

                </Select>

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

async function fetchCountries() {
    const response = await axios(
        {
            method: 'GET',
            url: path + "addressCountries"
        });
    return await response.data;
}

async function sendRegister(customer) {
    const response = await axios(
        {
            method: 'POST',
            url: path + 'customer/save',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            data: JSON.stringify(customer),
        });
    return await response.data;
}

export default CustomerRegister;