<script setup lang="js">
import * as d3 from 'd3'
import { onMounted, ref, watch } from 'vue'

const props = defineProps({
    data: {
        type: Array,
        required: true,
        default: () => []
    }
})

const chartContainer = ref(null)

const drawChart = () => {
    if (!chartContainer.value || props.data.length === 0) return

    d3.select(chartContainer.value).selectAll("*").remove()

    const margin = { top: 20, right: 30, bottom: 60, left: 70 }
    const width = chartContainer.value.clientWidth - margin.left - margin.right
    const height = 400 - margin.top - margin.bottom

    const svg = d3.select(chartContainer.value)
        .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", `translate(${margin.left},${margin.top})`)

    const x = d3.scaleBand()
        .domain(props.data.map(d => d.date))
        .range([0, width])
        .padding(0.3)

    const y = d3.scaleLinear()
        .domain([0, d3.max(props.data, d => d.totalAmount) * 1.1])
        .nice()
        .range([height, 0])

    svg.append("g")
        .attr("transform", `translate(0,${height})`)
        .call(d3.axisBottom(x))
        .selectAll("text")
        .classed("not-lg:transform not-lg:translate-y-5 not-lg:-translate-x-6 not-lg:-rotate-45 not-lg:text-left", true)

    svg.append("g")
        .call(d3.axisLeft(y).ticks(6).tickFormat(d => `$${d.toLocaleString()}`))

    svg.selectAll(".bar")
        .data(props.data)
        .enter()
        .append("rect")
        .attr("class", "bar")
        .attr("x", d => x(d.date))
        .attr("y", d => y(d.totalAmount))
        .attr("width", x.bandwidth())
        .attr("height", d => height - y(d.totalAmount))
        .classed("fill-primary", true)
        .classed("hover:fill-primary/80", true)
        .attr("rx", 4)


    svg.selectAll(".label")
        .data(props.data)
        .enter()
        .append("text")
        .text(d => `$${Math.round(d.totalAmount)}`)
        .attr("x", d => x(d.date) + x.bandwidth() / 2)
        .attr("y", d => y(d.totalAmount) - 5)
        .attr("text-anchor", "middle")
        .style("font-size", "12px")
        .classed("fill-base-content", true)
}

onMounted(() => {
    drawChart()
    window.addEventListener('resize', drawChart)
})

watch(() => props.data, () => {
    drawChart()
}, { deep: true })
</script>

<template>
    <div class="card bg-base-200 shadow-xl">
        <div class="card-body">
            <h3 class="card-title">Ventas del mes</h3>
            <div ref="chartContainer" class="chart-wrapper"></div>
        </div>
    </div>
</template>

<style>
@reference "@/assets/css/main.css";

.helper {
    @apply not-lg:transform not-lg:translate-y-5 not-lg:-translate-x-6 not-lg:-rotate-45 not-lg:text-left fill-base-content hover:fill-primary/80
}
</style>