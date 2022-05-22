import {useEffect, useState} from "react";
import {get} from "../utils";
import {useNavigate} from "react-router-dom";
import axios from "axios";

function CustomerBets() {
    const navigate = useNavigate()
    const [tokens, setTokens] = useState(get("tokens"))
    const [customer, setCustomer] = useState(get("customer-info"))
    const [bets, setBets] = useState()

    useEffect(() => {
        if (customer === null)
            navigate('/customer/login')

        getBets(customer, tokens)
            .then(bets => {
                setBets(bets)
            })
            .catch(error => console.error(error.response.data.message))
    }, [])

    return (
        <div>
            <h1>Customer Bets</h1>

            {bets?.map(bet => {
                let winner;
                if (bet.winner === null)
                    winner = "NULL";
                else if (bet.winner === true)
                    winner = "TRUE";
                else winner = "FALSE";
                return <div key={bet.betId}>
                    <h3>BetId: {bet.betId} -- Amount: {bet.amount}
                        {bet.odds.map(odd => {
                            let winner;
                            if (odd.winner === null)
                                winner = "NULL";
                            else if (odd.winner === true)
                                winner = "TRUE";
                            else winner = "FALSE";
                            return <li key={odd.id}>
                                {odd.id} --
                                {odd.oddType} -->
                                {odd.odd} --> 
                                {winner}
                            </li>
                        })}
                        <br/>
                        Winner: {winner}
                    </h3>
                </div>
            })}
        </div>
    )
}

const path = 'http://localhost:8080/';

async function getBets(customer, tokens) {
    const response = await axios(
        {
            method: 'GET',
            url: path + 'customer/getBets',
            headers: {
                'Content-Type': 'application/json',
                Authorization: "Bearer " + tokens.accessToken
            },
            params: {
                customerId: customer.customerId
            }
        });
    return await response.data;
}

export default CustomerBets;