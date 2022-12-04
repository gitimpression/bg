<template>

      <div class="card" @click="cardClick">
    {{ menu.remark }}
  </div>
  
</template>

<script>
export default {
  name: "Card",
  props: ["menu"],
  methods:{
    cardClick(){
      this.$emit("showHandbookDialog")
    }
  }
};
</script>

<style scoped>
.card {
  width: 20%;
  height: 120px;
  background-color: skyblue;
  margin: 10px;
  text-align: center;
  line-height: 120px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: box-shadow 0.2s;
  border-radius: 8px;
}
.card:hover {
  box-shadow: 5px 5px 20px grey;
}
</style>