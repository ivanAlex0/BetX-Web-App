import {useEffect, useState} from "react";
import {get} from "../utils";
import {useNavigate} from "react-router-dom";
import {Button, Form} from "react-bootstrap";
import axios from "axios";

function CustomerSearchTeam() {
    const navigate = useNavigate()
    const [tokens, setTokens] = useState(get("tokens"))
    const [customer, setCustomer] = useState(get('customer-info'))
    const [team, setTeam] = useState()
    const [dbTeam, setDbTeam] = useState([])
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

    }, [])

    function handleChange(event) {
        let {name, value} = event.target
        setTeam(prevState => {
            return {
                ...prevState,
                [name]: value
            }
        })
    }

    function handleSubmit(event) {
        console.log(team)
        searchTeam(team, tokens)
            .then(response => {
                setDbTeam(response)
                console.log(response)
            })
            .catch(error => console.error(error.response.data.message))
        event.preventDefault()
    }

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
                window.location.reload(false)
            })
            .catch(error => {
                setError(error.response.data.error_message)
                console.error(error.response.data.error_message)
            })
    }


    return (
        <div>
            <h1>
                Customer Home
            </h1>
            <h3>
                Wallet: {customer.wallet.balance}
            </h3>

            <Form.Group className={'mb-3'}>
                <Form.Label>Amount</Form.Label>
                <Form.Control
                    name={'amount'}
                    type={'number'}
                    placeholder={'Amount to bet...'}
                    onChange={handleChangeOdd}/>
            </Form.Group>
            <button onClick={send}>Send</button>

            <h3>{error}</h3>

            {odds.map(odd => {
                return <li key={odd.id}>
                    ID: {odd.id} --
                    {odd.oddType} -->
                    {odd.odd}
                </li>
            })}

            <h2>
                Search team by name & country
            </h2>

            <Form onSubmit={handleSubmit}>
                <Form.Group className={'mb-3'}>
                    <Form.Label>Team name</Form.Label>
                    <Form.Control
                        name={'name'}
                        type={'text'}
                        placeholder={'Enter team name...'}
                        onChange={handleChange}/>
                </Form.Group>

                <Form.Group className={'mb-3'}>
                    <Form.Label>Country</Form.Label>
                    <Form.Control
                        name={'country'}
                        type={'text'}
                        placeholder={'Enter country...'}
                        onChange={handleChange}/>
                </Form.Group>

                <Button variant="success" type="submit" style={{width: 400}}>
                    Login
                </Button>
            </Form>

            <h1>Home fixtures</h1>
            {
                dbTeam.homeFixtures?.map(fix => {
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

            <h1>Away fixtures</h1>
            {
                dbTeam.awayFixtures?.map(fix => {
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
                                {odd.oddType} -->
                                {odd.odd}
                                <button>Add</button>
                            </li>
                        })}
                    </div>
                })
            }
        </div>
    );
}

const path = 'http://localhost:8080/';

async function searchTeam(team, tokens) {
    const response = await axios(
        {
            method: 'POST',
            url: path + "searchTeam",
            headers: {
                'Authorization': 'Bearer ' + tokens.accessToken
            },
            params: {
                name: team.name,
                country: team.country
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
            params:{
                customerId: customer.customerId
            },
            data: JSON.stringify(bet)
        });
    return await response.data;
}

export default CustomerSearchTeam;