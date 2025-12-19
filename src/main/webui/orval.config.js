import { defineConfig } from 'orval';


export default defineConfig({
    'PointOfSale': {
        input: './openapi.yaml',
        output: {
            mode: 'tags-split',
            target: './app/services/PointOfSaleService.ts',
            schemas: 'app/models',
            client: 'fetch',
            override: {
                mutator: {
                    path: './app/services/CustomFetch.ts',
                    name: 'customFetch',
                }
            }
        }
    }
});