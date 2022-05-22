import {useEffect, useState} from "react";
import {get} from "../utils";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {Form} from "react-bootstrap";

function CustomerWallet() {
    const navigate = useNavigate()
    const [tokens, setTokens] = useState(get("tokens"))
    const [customer, setCustomer] = useState(get("customer-info"))
    const [bets, setBets] = useState()
    const [error, setError] = useState()
    const [amount, setAmount] = useState()

    useEffect(() => {
        if (customer === null)
            navigate('/customer/login')
    }, [])

    function handleChange(event) {
        let {value} = event.target
        setAmount(value)
    }

    function deposit() {
        sendDeposit(customer, amount, tokens)
            .then(wallet => {
                let newCustomer = {
                    ...customer,
                    wallet: wallet
                }
                localStorage.setItem('customer-info', JSON.stringify(newCustomer))
                window.location.reload(false)
            })
            .catch(error => {
                console.log(error)
                setError(error.response.data.error_message)
            })
    }

    function withdraw() {
        sendWithdraw(customer, amount, tokens)
            .then(wallet => {
                let newCustomer = {
                    ...customer,
                    wallet: wallet
                }
                localStorage.setItem('customer-info', JSON.stringify(newCustomer))
                window.location.reload(false)
            })
            .catch(error => {
                console.log(error.response.data.error_message)
                setError(error.response.data.error_message)
            })
    }

    return (
        <div>
            <h1>Customer Wallet</h1>
            <h1> Amount: {customer.wallet.balance}</h1>

            <Form.Group className={'mb-3'}>
                <Form.Label>Deposit/Withdraw:</Form.Label>
                <Form.Control
                    name={'amount'}
                    type={'number'}
                    placeholder={'Enter amount...'}
                    onChange={handleChange}/>
            </Form.Group>

            <h1>{error}</h1>
            <button onClick={deposit}>Deposit</button>
            <button onClick={withdraw}>Withdraw</button>

        </div>
    )
}

const path = 'http://localhost:8080/';

async function sendDeposit(customer, amount, tokens) {
    const response = await axios(
        {
            method: 'POST',
            url: path + 'customer/deposit',
            headers: {
                'Content-Type': 'application/json',
                Authorization: "Bearer " + tokens.accessToken
            },
            params: {
                customerId: customer.customerId,
                amount: amount
            }
        });
    return await response.data;
}

async function sendWithdraw(customer, amount, tokens) {
    const response = await axios(
        {
            method: 'POST',
            url: path + 'customer/withdraw',
            headers: {
                'Content-Type': 'application/json',
                Authorization: "Bearer " + tokens.accessToken
            },
            params: {
                customerId: customer.customerId,
                amount: amount
            }
        });
    return await response.data;
}

async function sendLogin(credentials) {
    const response = await axios(
        {
            method: 'POST',
            url: path + 'customer/auth',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            data: JSON.stringify(credentials),
        });
    return await response.data;
}

export default CustomerWallet;