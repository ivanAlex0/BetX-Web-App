import {useEffect, useState} from "react";
import {get} from "../utils";
import {useNavigate} from "react-router-dom";
import {Form} from "react-bootstrap";
import axios from "axios";

function CustomerAllFixtures() {
    const navigate = useNavigate()
    const [tokens, setTokens] = useState(get("tokens"))
    const [customer, setCustomer] = useState(get('customer-info'))
    const [fixtures, setFixtures] = useState([])
    const [odds, setOdds] = useState([])
    const [bet, setBet] = useState({
        amount: 0
    })
    const [error, setError] = useState()

    useEffect(() => {
        if (tokens === null)
            navigate('/')
        if (customer === null)
            navigate('/')

        getAllFixtures(tokens)
            .then(fixtures => {
                setFixtures(fixtures)
            })
            .catch(error => {
                console.error(error)
            })
    }, [])


    function handleChangeOdd(event) {
        let {name, value} = event.target
        setBet(prevState => {
            return {
                ...prevState,
                odds: odds,
                [name]: value
            }
        })
    }

    function addOdd(odd) {
        setOdds(prevState => {
            return [...prevState, odd]
        })
    }

    function send() {
        if (bet.amount <= 0)
            setError("Amount is incorrect")
        else
            sendBet(customer, bet, tokens)
                .then(response => {
                    let newBalance = customer.wallet.balance - bet.amount
                    let newCustomer = {
                        ...customer,
                        wallet: {
                            ...customer.wallet,
                            balance: newBalance
                        }
                    }
                    localStorage.setItem("customer-info", JSON.stringify(newCustomer))
                })
                .catch(error => {
                    console.error(error.response.data.error_message)
                    setError(error.response.data.error_message)
                })
    }


    return (
        <div>
            <h1>
                All Fixtures
            </h1>
            <h3>
                Wallet: {customer?.wallet?.balance}
            </h3>

            <Form.Group className={'mb-3'}>
                <Form.Label>Amount</Form.Label>
                <Form.Control
                    name={'amount'}
                    type={'number'}
                    placeholder={'Amount to bet...'}
                    onChange={handleChangeOdd}/>
            </Form.Group>
            <button onClick={send}>Bet</button>

            <h3>{error}</h3>

            {odds.map(odd => {
                return <li key={odd.id}>
                    ID: {odd.id} --
                    {odd.oddType} -->
                    {odd.odd}
                </li>
            })}

            <h1>Away fixtures</h1>
            {
                fixtures?.map(fix => {
                    let date = new Date(fix.timestamp * 1000)
                    return <div key={fix.id}>
                        <h1>{fix.home.name} -
                            {fix.away.name}
                        </h1>
                        <h3>
                            {date.getDate()} {date.getMonth()} {date.getFullYear()}
                        </h3>
                        {fix.odds?.map(odd => {
                            return <li key={odd.id}>
                                ID: {odd.id} --
                                {odd.oddType} -->
                                {odd.odd}
                                <button onClick={() => addOdd(odd)}>Add</button>
                            </li>
                        })}
                    </div>
                })
            }
        </div>
    );
}

const path = 'http://localhost:8080/';

async function getAllFixtures(tokens) {
    const response = await axios(
        {
            method: 'GET',
            url: path + "findAll",
            headers: {
                'Authorization': 'Bearer ' + tokens.accessToken
            }
        });
    return await response.data;
}

async function sendBet(customer, bet, tokens) {
    const response = await axios(
        {
            method: 'POST',
            url: path + "customer/placeBet",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + tokens.accessToken
            },
            params: {
                customerId: customer.customerId
            },
            data: JSON.stringify(bet)
        });
    return await response.data;
}

export default CustomerAllFixtures;