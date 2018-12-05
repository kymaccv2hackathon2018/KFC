const axios = require('axios')

module.exports = { main: async function (event, context) {
    var url = `${process.env.GATEWAY_URL}/electronics/users/kaitlin_brockway@outlook.com/orders`
    
    var response = await axios.get(url, {
        responseType: 'json'
    });

    console.log(JSON.stringify(response.data.orders[0]))
    return {
        "id": parseInt(response.data.orders[0].code, 10).toString(), 
        "status": response.data.orders[0].statusDisplay
    } 
}}