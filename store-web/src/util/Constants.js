const getProducerHostname = () => {
    if (process.env.REACT_APP_PRODUCER_URL != undefined) {
        return process.env.REACT_APP_PRODUCER_URL
    }
    return 'http://localhost:8080'
}

export const PRODUCER_URL = getProducerHostname() + '/store/api/'